package tr.edu.bilecik.studentattendancecontrolsystem.Model;

/**
 * Created by gurkanmustafa on 07/10/2015.
 */
public class Lesson {

    private String objectId;
    private String lessonName;

    public Lesson(String objectId, String lessonName)
    {
        this.setObjectId(objectId);
        this.setLessonName(lessonName);
    }


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }
}
