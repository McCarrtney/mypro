package com.dzqc.cloud.controller;

import com.dzqc.cloud.common.Message;
import com.dzqc.cloud.common.ResultObject;
import com.dzqc.cloud.dto.DoctorBasicInfo;
import com.dzqc.cloud.dto.MedicalInfo;
import com.dzqc.cloud.dto.UserBasicInfo;
import com.dzqc.cloud.dto.UserHealthInfo;
import com.dzqc.cloud.entity.*;
import com.dzqc.cloud.service.EmpinfoService;
import com.dzqc.cloud.service.HealthRecordService;
import com.dzqc.cloud.service.MedicalrecordService;
import com.dzqc.cloud.service.UserService;
import com.dzqc.cloud.util.JwtTokenUtil;
import com.dzqc.cloud.util.SendSmsUtil;
import com.dzqc.cloud.util.VerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class UserinfoController {
    @Autowired
    private SendSmsUtil sendSmsUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private HealthRecordService healthRecordService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private EmpinfoService empinfoService;
    @Autowired
    private MedicalrecordService medicalrecordService;
    /**
     * 根据手机号查询用户信息
     * @return
     */
    @RequestMapping("/user/findByPhone")
    /**
     * RequestMapping 是一个用来处理请求地址映射的注解，可用于类或方法上。
     *  用于类上：表示类中的所有响应请求的方法都是以该地址作为父路径
     *  用于方法上: 提供进一步的细分映射信息
     */
    public ResultObject findByPhone(String phone) {
        try {
            Userinfo userinfo = this.userService.selectByPhone(phone);
            //
            if (userinfo != null) {
                return ResultObject.success(userinfo);
            } else {
                return ResultObject.error("查询的" + phone + "手机号不存在");
            }
        } catch (Exception e) {
            return ResultObject.error(Message.SERVER_ERROR);
        }
    }
    /**
     * 退出
     * @param request
     * @returnd
     */
    @RequestMapping("/shiro/logout")
    public ResultObject logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("userinfo");
        return ResultObject.success("成功注销");
    }

    /**
     * 用户登陆
     * @param phone 手机号
     * @param password 密码
     * @return
     */

    //初始版本
    /*@PostMapping("/shiro/login")
    public ResultObject login(String phone,String code,HttpServletRequest request){

        HttpSession session = request.getSession();
        //从session中获取验证码
        String sessionCode = (String)session.getAttribute(phone);
        //对验证码进行校验
        if(sessionCode != null && sessionCode.equals(code)){
            Userinfo userinfo = userService.selectByPhone(phone);
            if(userinfo == null){
                return ResultObject.error("号码未注册，请联系管理员");
            }else{
                session.setAttribute("userinfo",userinfo);
                return ResultObject.success(userinfo);
            }
        }else{
            return ResultObject.error("验证码错误或者已经过期");
        }
    }*/

    //更新版本 2021/5/22 wxr
    /*
    @RequestMapping(value = "/test/login", method = RequestMethod.POST)
    public  ResultObject login(@RequestParam(name = "phone", required = true) String phone, @RequestParam(name = "password", required = true) String password, HttpServletRequest request){
        HttpSession session = request.getSession();
        Userinfo userinfo = userService.selectByPhone(phone);
        if(userinfo == null){
            return ResultObject.error("号码未注册，请先完成注册");
        }else{
            String truePassword = userinfo.getPassword();
            if(!password.equals(truePassword)){
                return ResultObject.error("密码错误");
            }else{
                session.setAttribute("userinfo",userinfo);
                return ResultObject.success(userinfo);
            }
        }
    }

     */

    /**
     * 用户登陆
     * @param phone 手机号
     * @param password 密码
     * @return token
     */

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public  ResultObject login(@RequestParam(name = "phone", required = true) String phone, @RequestParam(name = "password", required = true) String password){
        Userinfo userinfo = userService.selectByPhone(phone);
        if(userinfo == null){
            return ResultObject.error("号码未注册，请先完成注册", 904);
        }else{
            String token = userService.login(phone, password);
            return ResultObject.success(token);
        }
    }

    /**
     * 医生登陆
     * @param phone 手机号
     * @param password 密码
     * @return token
     */

    @RequestMapping(value = "/doctor/login", method = RequestMethod.POST)
    @ResponseBody
    public  ResultObject loginDoctor(@RequestParam(name = "phone", required = true) String phone, @RequestParam(name = "password", required = true) String password){
        Userinfo userinfo = userService.selectByPhone(phone);
        if(userinfo == null){
            return ResultObject.error("号码未注册，请先完成注册", 904);
        }else{
            String token = userService.login(phone, password);
            return ResultObject.success(token);
        }
    }

    /**
     * 管理员登陆
     * @param phone 手机号
     * @param password 密码
     * @return token
     */

    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    @ResponseBody
    public  ResultObject loginAdmin(@RequestParam(name = "phone", required = true) String phone, @RequestParam(name = "password", required = true) String password){
        Userinfo userinfo = userService.selectByPhone(phone);
        if(userinfo == null){
            return ResultObject.error("号码未注册，请先完成注册", 904);
        }else{
            String token = userService.login(phone, password);
            return ResultObject.success(token);
        }
    }

    /**
     * 注册
     * @param phone 手机号
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject register(@RequestParam(name = "phone", required = true) String phone,
                                 @RequestParam(name = "password", required = true) String password,
                                 @RequestParam(name = "username", required = true) String username,
                                 @RequestParam(name = "role", required = true) int role){
        if(userService.selectByPhone(phone)!=null){
            return ResultObject.error("该号码已经注册",902);
        }else{
            Userinfo userinfo = new Userinfo();
            userinfo.setPhone(phone);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String bpassword = passwordEncoder.encode(password.trim());
            userinfo.setPassword(bpassword);
            userinfo.setUsername(username);
            userinfo.setRoleid(role);
            userinfo.setBirthday(new Date(2021-1900, 5-1, 22));
            userinfo.setState(1);
            if(userService.insertUser(userinfo)==0){
                return ResultObject.error("注册失败，请重试或联系管理员",903);
            }else{
                return ResultObject.success(userinfo);
            }
        }
    }


    /**
     * 发送验证码
     * @param phone 手机号
     * @return
     */
    @RequestMapping(value = "/shiro/getCode",method = RequestMethod.GET)
    @ResponseBody
    public ResultObject sendSms(String phone, HttpServletRequest request){
        Userinfo userinfo=userService.selectByPhone(phone);
        if(userinfo != null) {
            HttpSession session = request.getSession();
            //随机生成验证码
            String numeral = VerificationCode.getNumeral();
            //String information = sendSmsUtil.aliSendSms(phone, numeral);
            //session.setAttribute(phone, numeral);
            session.setAttribute(phone, numeral);
            //验证码过期时间，三分钟
            session.setMaxInactiveInterval(3 * 60);
            /*if (information.equals("ok")) {
                return ResultObject.success(numeral);
            }*/
            if (numeral!=null){
                 return ResultObject.success(numeral);
            }
            return ResultObject.error("手机验证码发送失败");
        }else{
            return ResultObject.error("号码未注册，请联系管理员");
        }
    }

    /**
     * 获取用户基本信息
     * @param phone 用户phone
     * @return 用户信息
     */
    @RequestMapping(value = "/user/getInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResultObject getUserBasicInfo(@RequestParam(name = "phone", required = true) String phone){
        //String phone = jwtTokenUtil.getUsernameFromToken(token);
        Userinfo userinfo = userService.selectByPhone(phone);
        if(userinfo==null){
            return ResultObject.error("用户不存在", 904);
        }
        UserBasicInfo userBasicInfo = new UserBasicInfo(userinfo.getId(), userinfo.getUsername(), userinfo.getBirthday(), userinfo.getPicture());
        return ResultObject.success(userBasicInfo);
    }

    /**
     * 获取用户健康信息
     * @param phone 用户phone
     * @return 用户信息
     */
    @RequestMapping(value = "/user/getHealth", method = RequestMethod.GET)
    @ResponseBody
    public ResultObject getUserHealthInfo(@RequestParam(name = "phone", required = true) String phone){
        //String phone = jwtTokenUtil.getUsernameFromToken(token);
        Userinfo userinfo = userService.selectByPhone(phone);
        if(userinfo==null){
            return ResultObject.error("用户不存在", 904);
        }
        int uid = userinfo.getId();
        HealthRecord healthRecord = healthRecordService.selectByUserID(uid);
        if(healthRecord==null){
            return ResultObject.error("该用户无健康记录", 905);
        }
        return ResultObject.success(new UserHealthInfo(healthRecord.getDate(), healthRecord.getHeight(), healthRecord.getWeight(), healthRecord.getHighpressure(), healthRecord.getLowpressure(), healthRecord.getLung()));
    }

    /**
     * 获取医生
     * @param phone 用户phone
     * @return 医生信息
     */
    @RequestMapping(value = "/user/getDoctor", method = RequestMethod.GET)
    @ResponseBody
    public ResultObject getAllDoctor(@RequestParam(name = "phone", required = true) String phone){
        List<Empinfo> empinfos = empinfoService.selectAll();
        if(empinfos==null){
            return ResultObject.error("没找到医生", 906);
        }
        List<DoctorBasicInfo> doctorBasicInfos = new ArrayList<>();
        for(Empinfo empinfo:empinfos){
            doctorBasicInfos.add(new DoctorBasicInfo(empinfo.getId(), empinfo.getBirthday(), empinfo.getUsername(), empinfo.gethospital(), empinfo.getoffice(), empinfo.getComment(), empinfo.getHeadimg()));
        }
        return ResultObject.success(doctorBasicInfos);
    }

    /**
     * 获取用户病历
     * @param phone 用户phone
     * @return 病历信息
     */
    @RequestMapping(value = "/user/getMedical", method = RequestMethod.GET)
    @ResponseBody
    public ResultObject getMedical(@RequestParam(name = "phone", required = true) String phone){
        //String phone = jwtTokenUtil.getUsernameFromToken(token);
        Userinfo userinfo = userService.selectByPhone(phone);
        if(userinfo==null){
            return ResultObject.error("用户不存在", 904);
        }
        Integer uid = userinfo.getId();
        List<Medicalrecord> medicalrecords = medicalrecordService.selectByUserID(uid);
        if(medicalrecords==null){
            return ResultObject.error("该用户无病历", 907);
        }
        List<MedicalInfo> medicalInfos = new ArrayList<>();
        for(Medicalrecord medicalrecord:medicalrecords){
            String name;
            Empinfo empinfo = medicalrecord.getEmpinfo();
            if(empinfo==null){
                name = "未知";
            }else {
                name = empinfo.getUsername();
            }
            Integer rid = medicalrecord.getId();
            List<Prescription> prescriptions = medicalrecordService.selectPrescription(rid);
            if(prescriptions==null){
                medicalInfos.add(new MedicalInfo(medicalrecord.getId(), medicalrecord.getCreateTime(), name, medicalrecord.getDiagnosis(), new ArrayList<>()));
            }else{
                medicalInfos.add(new MedicalInfo(medicalrecord.getId(), medicalrecord.getCreateTime(), name, medicalrecord.getDiagnosis(), prescriptions));
            }
        }
        return ResultObject.success(medicalInfos);
    }
}