package android.example.jobschedulerexample;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class ExampleJobService extends JobService {

    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "Job has started");
        doBackgroundWork(jobParameters);

        return true;
    }

    private void doBackgroundWork(JobParameters jobParameters) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Log.d(TAG, "run: " + i);
                    if (jobCancelled) {
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d(TAG, "Job finished");
                jobFinished(jobParameters, false);

            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }
}
