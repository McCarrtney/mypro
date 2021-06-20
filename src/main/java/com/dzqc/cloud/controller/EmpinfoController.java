package com.dzqc.cloud.controller;

import com.dzqc.cloud.common.ResultObject;
import com.dzqc.cloud.dto.DoctorBasicInfo;
import com.dzqc.cloud.dto.MedicalInfo;
import com.dzqc.cloud.dto.PatientInfo;
import com.dzqc.cloud.entity.Medicalrecord;
import com.dzqc.cloud.entity.Prescription;
import com.dzqc.cloud.entity.Userinfo;
import com.dzqc.cloud.service.EmpinfoService;
import com.dzqc.cloud.common.Message;
import com.dzqc.cloud.entity.Empinfo;
import com.dzqc.cloud.service.MedicalrecordService;
import com.dzqc.cloud.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EmpinfoController {
    @Autowired
    private EmpinfoService empinfoService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private MedicalrecordService medicalrecordService;
    /**
     * 根据科室查询 该科室的医生
     * @param depid 部门id
     * @return
     */
    @RequestMapping("empinfo/findByDid")
    public ResultObject findByDid(@RequestParam("id") Integer depid){
       try {
    	   List<Empinfo> empinfolist = empinfoService.findByDid(depid);
    	   return ResultObject.success(empinfolist);
       }catch (Exception e){
           return ResultObject.error(Message.SERVER_ERROR);
       }
    }
    /**
     * 新增科室医生
     */
    @RequestMapping("empinfo/insertEmp")
    public ResultObject insertEmp(Empinfo empinfo){
        try {
            empinfoService.insertEmp(empinfo);
            return ResultObject.success("添加医生成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObject.error("添加医生失败");
        }
    }

    /**
     * 获取医生
     * @param phone 用户phone
     * @return 医生信息
     */
    @RequestMapping(value = "/doctor/getInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResultObject getDoctorInfo(@RequestParam(name = "phone", required = true) String phone){
        //String phone = jwtTokenUtil.getUsernameFromToken(token);
        Empinfo empinfo = empinfoService.selectByPhone(phone);
        if(empinfo==null){
            return ResultObject.error("用户不存在", 904);
        }
        DoctorBasicInfo doctorBasicInfo = new DoctorBasicInfo(empinfo.getId(), empinfo.getBirthday(), empinfo.getUsername(), empinfo.gethospital(), empinfo.getoffice(), empinfo.getComment(), empinfo.getHeadimg());
        return ResultObject.success(doctorBasicInfo);
    }

    /**
     * 获取病人
     * @param phone 用户phone
     * @return 病人信息
     */
    @RequestMapping(value = "/doctor/getPatient", method = RequestMethod.GET)
    @ResponseBody
    public ResultObject getPatientInfo(@RequestParam(name = "phone", required = true) String phone){
        //String phone = jwtTokenUtil.getUsernameFromToken(token);
        Empinfo empinfo = empinfoService.selectByPhone(phone);
        if(empinfo==null){
            return ResultObject.error("用户不存在", 904);
        }
        Integer did = empinfo.getId();
        List<Medicalrecord> medicalrecords = medicalrecordService.selectByDoctorID(did);
        if(medicalrecords==null){
            return ResultObject.error("该医生无病人", 907);
        }
        List<PatientInfo> patientInfos = new ArrayList<>();
        for(Medicalrecord medicalrecord:medicalrecords){
            String name;
            Userinfo userinfo = medicalrecord.getUserinfo();
            if(userinfo==null){
                name = "未知";
            }else {
                name = userinfo.getUsername();
            }
            Integer rid = medicalrecord.getId();
            List<Prescription> prescriptions = medicalrecordService.selectPrescription(rid);
            if(prescriptions==null){
                patientInfos.add(new PatientInfo(medicalrecord.getCreateTime(), name, medicalrecord.getState(), medicalrecord.getDiagnosis(), new ArrayList<>()));
            }else{
                patientInfos.add(new PatientInfo(medicalrecord.getCreateTime(), name, medicalrecord.getState(), medicalrecord.getDiagnosis(), prescriptions));
            }
        }
        return ResultObject.success(patientInfos);
    }


}
