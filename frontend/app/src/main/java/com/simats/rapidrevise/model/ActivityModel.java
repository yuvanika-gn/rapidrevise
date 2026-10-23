package com.simats.rapidrevise.model;

public class ActivityModel {
    int user_id, subject_id, time_spent;
    String feature_type, action_type;

    public ActivityModel(int user_id, int subject_id,
                         String feature_type, String action_type, int time_spent) {
        this.user_id = user_id;
        this.subject_id = subject_id;
        this.feature_type = feature_type;
        this.action_type = action_type;
        this.time_spent = time_spent;
    }
}
