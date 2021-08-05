package com.xl.core;

import org.apache.spark.scheduler.*;

public class SparkListenerUser extends SparkListener {

    @Override
    public void onExecutorRemoved( SparkListenerExecutorRemoved executorRemoved) {
    }

    /**
     * Called when a stage completes successfully or fails, with information on the completed stage.
     */
    @Override
    public void onStageCompleted( SparkListenerStageCompleted stageCompleted) {

    }

    @Override
    public void onStageSubmitted( SparkListenerStageSubmitted stageSubmitted) {

    }

    @Override
    public void onTaskStart(SparkListenerTaskStart taskStart) {

    }
    /**
     * Called when a job ends
     */
    @Override
    public void onJobEnd(SparkListenerJobEnd jobEnd) {
        JobResult jobResult = jobEnd.jobResult();
        System.err.println("自定义监听器jobEnd jobResult:"+jobResult);
    }
    /**
     * Called when a job starts
     */
    @Override
    public void onJobStart(SparkListenerJobStart jobStart) {
        System.err.println("自定义监听器jobStart,jobId:"+jobStart.jobId());
        System.err.println("自定义监听器jobStart,该job下stage数量："+jobStart.stageInfos().size());
    }

    @Override
    public void onExecutorMetricsUpdate(SparkListenerExecutorMetricsUpdate executorMetricsUpdate) {

    }

    @Override
    public void onExecutorAdded(SparkListenerExecutorAdded executorAdded) {

    }

    @Override
    public void onNodeUnblacklisted(SparkListenerNodeUnblacklisted nodeUnblacklisted) {

    }
    /**
     * Called when the application ends
     */
    @Override
    public void onApplicationEnd(SparkListenerApplicationEnd applicationEnd) {
        System.err.println("Application结束，时间："+applicationEnd.time());
    }

    @Override
    public void onNodeBlacklisted(SparkListenerNodeBlacklisted nodeBlacklisted) {

    }

    @Override
    public void onUnpersistRDD(SparkListenerUnpersistRDD unpersistRDD) {

    }

    @Override
    public void onTaskGettingResult(SparkListenerTaskGettingResult taskGettingResult) {

    }

    @Override
    public void onOtherEvent(SparkListenerEvent event) {

    }

    @Override
    public void onEnvironmentUpdate(SparkListenerEnvironmentUpdate environmentUpdate) {

    }

    @Override
    public void onSpeculativeTaskSubmitted(SparkListenerSpeculativeTaskSubmitted speculativeTask) {

    }

    @Override
    public void onExecutorBlacklisted(SparkListenerExecutorBlacklisted executorBlacklisted) {

    }

    @Override
    public void onBlockManagerRemoved(SparkListenerBlockManagerRemoved blockManagerRemoved) {

    }
    /**
     * Called when the application starts
     */
    @Override
    public void onApplicationStart(SparkListenerApplicationStart applicationStart) {
        System.err.println("Application启动，appName:"+applicationStart.appName()+",appID"+
                applicationStart.appId());
    }

    @Override
    public void onExecutorUnblacklisted(SparkListenerExecutorUnblacklisted executorUnblacklisted) {

    }

    @Override
    public void onBlockManagerAdded(SparkListenerBlockManagerAdded blockManagerAdded) {

    }

    @Override
    public void onBlockUpdated(SparkListenerBlockUpdated blockUpdated) {

    }

    @Override
    public void onTaskEnd(SparkListenerTaskEnd taskEnd) {

    }

}
