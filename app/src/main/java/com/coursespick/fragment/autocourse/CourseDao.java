package com.coursespick.fragment.autocourse;

import java.util.ArrayList;
import java.util.List;

/*
 * created by zhangziqiang on 17/2/18
 */
public class CourseDao {
    /**
        非智能选课，实现选已经排好的课
        courseData需要完全无冲突
    */
    static List<CourseModel> courseModels[] = new ArrayList[7];
    public static void setCourseData(CourseModel[] courseData){
        for (int i = 0; i < courseModels.length; i++) {
            courseModels[i] = new ArrayList<>();
        }
        for(int i=0;i<courseData.length;++i){
            courseModels[courseData[i].getWeek()-1].add(courseData[i]);
        }
    }

    public static List<CourseModel>[] getCourseData() {
        return courseModels;
    }
}
