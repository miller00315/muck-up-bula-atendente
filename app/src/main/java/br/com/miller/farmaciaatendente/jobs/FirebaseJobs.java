package br.com.miller.farmaciaatendente.jobs;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;

import br.com.miller.farmaciaatendente.MainActivity;
import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Buy;
import br.com.miller.farmaciaatendente.utils.StringUtils;

public class FirebaseJobs extends JobService {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public static final String ID = FirebaseJobs.class.getName();
    private boolean isWorking = false;
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(@NonNull JobParameters job) {

        isWorking = true;

        jobCancelled = false;

        startWork(job);

        return isWorking;
    }


    @Override
    public boolean onStopJob(@NonNull JobParameters params) {

        jobCancelled = true;

        removeListener(params);

        jobFinished(params, isWorking);

        return jobCancelled;
    }

    public void removeListener(JobParameters params){

        firebaseDatabase.getReference()
                .child("buys")
                .child(Objects.requireNonNull(Objects.requireNonNull(params.getExtras()).getString("city")))
                .child("stores")
                .child(Objects.requireNonNull(params.getExtras().getString("storeId")))
                .child("news")
                .removeEventListener(childEventListener);

    }

    public void startWork(final JobParameters params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                checkModifications(params);
            }
        }).start();
    }

    private void checkModifications(JobParameters params){

        firebaseDatabase.getReference()
                .child("buys")
                .child(Objects.requireNonNull(Objects.requireNonNull(params.getExtras()).getString("city")))
                .child("stores")
                .child(Objects.requireNonNull(params.getExtras().getString("storeId")))
                .child("news")
                .addChildEventListener(childEventListener);

    }

    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            if(dataSnapshot.exists()) {

                if (!isAppOnForeground(getApplicationContext())) {
                    try {
                        sendNotification(dataSnapshot.getValue(Buy.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    public void sendNotification(Buy buy){

        if(buy.getStatus().equals("news")){

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationCompat =
                    new NotificationCompat.Builder(getApplicationContext(),
                            getString(R.string.default_notification_channel_id))
                            .setTicker("Novo pedido")
                            .setContentTitle("Novo pedido")
                            .setContentText("De: ".concat(buy.getUserName()))
                            .setSubText("Às: ".concat(StringUtils.formatDate(buy.getSolicitationDate())))
                            .setAutoCancel(true)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setSound(defaultSoundUri)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setVibrate(new long[]{1000, 1000, 1000, 1000});

            if (notificationCompat != null) {

                notificationCompat.setContentIntent(sendNotificationBundle(MainActivity.class));

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

                notificationManager.notify((int) System.currentTimeMillis(), notificationCompat.build());

            }
        }

    }

    public PendingIntent sendNotificationBundle(Class classSolicitada){

        Intent intent = new Intent(getApplicationContext(), classSolicitada);

        return PendingIntent.getActivity(this, 23, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    private boolean isAppOnForeground(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager != null){

            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses == null) {
                return false;
            }
            final String packageName = context.getPackageName();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }else{
            return  true;
        }
    }
}
