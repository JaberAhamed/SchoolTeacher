package com.teacher.modern.modernteacher.connectivity;

import com.teacher.modern.modernteacher.models.ExamType;
import com.teacher.modern.modernteacher.models.Message;
import com.teacher.modern.modernteacher.models.Notice;
import com.teacher.modern.modernteacher.models.Subject;
import com.teacher.modern.modernteacher.models.User;
import com.teacher.modern.modernteacher.models.viewmodels.AttendanceVM;
import com.teacher.modern.modernteacher.models.viewmodels.AttendanceVMCollections;
import com.teacher.modern.modernteacher.models.viewmodels.ClassSectionIdsVM;
import com.teacher.modern.modernteacher.models.viewmodels.ClassSectionNamesVM;
import com.teacher.modern.modernteacher.models.viewmodels.GetAttendanceVM;
import com.teacher.modern.modernteacher.models.viewmodels.InputExamMarksVM;
import com.teacher.modern.modernteacher.models.viewmodels.LoginVM;
import com.teacher.modern.modernteacher.models.viewmodels.PostNoticeVM;
import com.teacher.modern.modernteacher.models.viewmodels.RequestClassTestMarksVM;
import com.teacher.modern.modernteacher.models.viewmodels.RequestExamMarksVM;
import com.teacher.modern.modernteacher.models.viewmodels.StudentGuardianVM;
import com.teacher.modern.modernteacher.service_models.basic_models.GroupBasic;
import com.teacher.modern.modernteacher.service_models.basic_models.UserBasic;
import com.teacher.modern.modernteacher.service_models.basic_response_models.CommonResponse;
import com.teacher.modern.modernteacher.service_models.chat_models.ChatParameters;
import com.teacher.modern.modernteacher.service_models.chat_models.ChatResponse;
import com.teacher.modern.modernteacher.service_models.chat_models.MessageInfo;
import com.teacher.modern.modernteacher.service_models.exam_models.ExamGroupBasic;
import com.teacher.modern.modernteacher.service_models.exam_models.ExamGroupResponse;
import com.teacher.modern.modernteacher.service_models.exam_models.ExamInfo;
import com.teacher.modern.modernteacher.service_models.exam_models.ExamInfoResponse;
import com.teacher.modern.modernteacher.service_models.login_models.LoginInfo;
import com.teacher.modern.modernteacher.service_models.login_models.LoginResponse;
import com.teacher.modern.modernteacher.service_models.members_models.MemberResponse;
import com.teacher.modern.modernteacher.service_models.notice_models.NoticeInfo;
import com.teacher.modern.modernteacher.service_models.notice_models.NoticeResponse;
import com.teacher.modern.modernteacher.service_models.notice_models.ParameterGroupResponse;
import com.teacher.modern.modernteacher.service_models.students_model.AttendanceGroup;
import com.teacher.modern.modernteacher.service_models.students_model.SaveAttendanceModel;
import com.teacher.modern.modernteacher.service_models.students_model.StudentAttendanceInfoResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiInterface {

    @POST("Security/UserLogin")
    Call<LoginResponse> getLoginResponse(@Body LoginInfo loginInfo);

    @POST("Message/PostNotice")
    Call<CommonResponse> getNoticeResponse(@Body NoticeInfo noticeInfo);

    @POST("Message/GetAllNotices")
    Call<NoticeResponse> getAllNotices(@Body UserBasic userBasic);

    @POST("Message/GetMyMessages")
    Call<ChatResponse> getAllChat(@Body ChatParameters chatParameters);

    @POST("Message/SendMessage")
    Call<CommonResponse> sendMyMessage(@Body MessageInfo messageInfo);


    @POST("Message/GetGroupInfo")
    Call<ParameterGroupResponse> getParameterGrpups(@Body UserBasic userBasic);

    @POST("AccountInfo/GetSubUserByGroup")
    Call<MemberResponse> getStudentInfoByGroup(@Body GroupBasic groupBasic);

    @POST("AccountInfo/GetSubUserAttendanceByGroup")
    Call<StudentAttendanceInfoResponse> getStudentAttendanceInfoByGroup(@Body AttendanceGroup attendanceGroup);

    @POST("AccountInfo/SaveAttendance")
    Call<CommonResponse> getSaveAttendanceResponse(@Body SaveAttendanceModel saveAttendanceModel);

    @POST("Exam/GetExamInfoByGroup")
    Call<ExamInfoResponse> getExamInfoByGroup(@Body ExamGroupBasic groupBasic);

    @POST("Exam/UpdateExamInfo")
    Call<CommonResponse> updateExamInfo(@Body ArrayList<ExamInfo> examInfoList);

    @POST("Exam/GetExamGroupsByGroupId")
    Call<ExamGroupResponse> getExamGroupInfoList(@Body GroupBasic groupBasic);

    // *************************************** //
    @POST("Login/login")
    Call<User> getLoginResponse(@Body LoginVM loginInfo);

    @POST("Notice/getAllNotices")
    Call<List<Notice>> getAllNotices();

    @GET
    Call<List<Subject>> getSubjectsByTeacherId(@Url String url);

    @GET
    Call<List<Notice>> getNoticesByUserId(@Url String url);

    @POST("Notice/postNotices")
    Call<Boolean> postNotice(@Body PostNoticeVM notice);

    @POST("Teacher/getClassNamesByClassIds")
    Call<ClassSectionNamesVM> getClassNamesByClassIds(@Body ClassSectionIdsVM classSectionIdVms);

    @GET
    Call<String> getLastUpdatedNoticeTime(@Url String url); //Notice/getLastUpdatedNoticeTime?uid=

    @GET
    Call<List<StudentGuardianVM>> getGuardianLists(@Url String url); //Guardian/getGuardianLists?classId=_&sectionId=_

    @GET
    Call<List<Message>> getMessages(@Url String url); //Message/getMessages?uId1=_&uId2=_

    @POST("Message/postMessage")
    Call<Boolean> postMessage(@Body Message message);

    @GET
    Call<List<ExamType>> getAllExamTypes(@Url String url); //Exam/getAllExamTypes

    @POST("Attendance/getAttendanceRecord")
    Call<List<AttendanceVM>> getAttendanceRecord(@Body GetAttendanceVM getAttendanceVM);

    @POST("Attendance/setOrUpdateAttendaces")
    Call<Boolean> setOrUpdateAttendaces(@Body AttendanceVMCollections collections);

    @POST("Exam/GetEditableExamMarksByClassIdSectionId")
    Call<List<InputExamMarksVM>> GetEditableExamMarksByClassIdSectionId(@Body RequestExamMarksVM requestExamMarksVM);

    @POST("Exam/GetViewableExamMarksByClassIdSectionId")
    Call<List<InputExamMarksVM>> GetViewableExamMarksByClassIdSectionId(@Body RequestExamMarksVM requestExamMarksVM);

    @POST("Exam/GetViewableClassTestMarksByClassIdSectionId")
    Call<List<InputExamMarksVM>> GetViewableClassTestMarksByClassIdSectionId(@Body RequestClassTestMarksVM requestExamMarksVM);

    @POST("Exam/GetEditableClassTestMarksByClassIdSectionId")
    Call<List<InputExamMarksVM>> GetEditableClassTestMarksByClassIdSectionId(@Body RequestClassTestMarksVM requestExamMarksVM);

    @POST("Exam/SetExamMarks")
    Call<Boolean> SetExamMarks(@Body List<InputExamMarksVM> inputExamMarksVMs);


    @POST("Exam/SetClassTestMarks")
    Call<Boolean> SetClassTestMarks(@Body List<InputExamMarksVM> inputExamMarksVMs);

}
