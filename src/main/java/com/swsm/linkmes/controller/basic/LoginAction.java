package com.swsm.linkmes.controller.basic;

import com.swsm.linkmes.domain.dto.LoginInfoQueryDto;
import com.swsm.linkmes.model.basic.LoginInfo;
import com.swsm.linkmes.model.basic.Resource;
import com.swsm.linkmes.model.basic.User;
import com.swsm.linkmes.service.basic.ILoginInfoService;
import com.swsm.linkmes.service.basic.ILoginService;
import com.swsm.linkmes.service.basic.IMainService;
import com.swsm.linkmes.vo.basic.LoginInfoQueryVo;
import com.swsm.linkmes.vo.basic.LoginInfoVo;
import com.swsm.linkmes.vo.basic.ResourceVo;
import com.swsm.linkmes.vo.basic.UserVo;
import com.swsm.platform.action.MultiResultResponse;
import com.swsm.platform.action.UniqueResultResponse;
import com.swsm.platform.model.PageFilter;
import com.swsm.platform.util.converter.entity.ConvertUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author tinel
 * @Title: LoginAction
 * @ProjectName mes-sm
 * @Description: LoginApi实现类
 * @date 2018/12/1213:23
 */
@RestController
public class LoginAction {

    @Autowired
    private ILoginService loginService;

    @Autowired
    private IMainService mainService;

    @Autowired
    private ILoginInfoService loginInfoService;

    @GetMapping("/basic/login/user/{loginName}")
    public UserVo findUser(@PathVariable("loginName") String loginName) {
        User user = loginService.findUser(loginName);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo,new String[]{"roleList","authorityId",
                "authorityList","organList","roleId"});
        return userVo;
    }

    @GetMapping("/basic/login/user/card/{workNo}")
    public UserVo getUserByWorkNo(@PathVariable("workNo") String workNo) {
        User user = loginService.getUserByWorkNo(workNo);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo,new String[]{"roleList","authorityId",
                "authorityList","organList","roleId"});
        return userVo;
    }

    @GetMapping("/basic/login/resource/{loginName}")
    public ResourceVo[] getHavResByLoginName(@PathVariable("loginName") String loginName) {
        Resource[] resources = this.mainService.getHavResByLoginName(loginName);
        List<Resource> list = Arrays.asList(resources);
        List<ResourceVo> results = ConvertUtil.toNList(list,Resource.class,ResourceVo.class);
        return results.toArray(new ResourceVo[0]);
    }

    @GetMapping("/basic/login/check/{loginName}/{localIp}")
    public boolean checkUserIsLogin(@PathVariable("loginName") String loginName, @PathVariable("localIp") String localIp) {
        return this.loginInfoService.checkUserIsLogin(loginName,localIp);
    }

    @GetMapping("/basic/login/card/check/{userName}")
    public String checkUserLoginFLag(@PathVariable("userName") String userName) {
        List<Map<String, Object>> userInfo;
        userInfo = this.loginInfoService.getUserInfoByUserName(userName);
        if (userInfo.size() == 0)
            return "false";
        return "true";
    }

    @GetMapping("/basic/login/status/check/{userName}/{localIp}")
    public UniqueResultResponse<String> checkLoginStatus(@PathVariable("userName") String userName, @PathVariable("localIp") String localIp) {
        String msg = this.loginInfoService.checkLoginStatus(userName,localIp);
        return new UniqueResultResponse<>(msg,msg);
    }



    @GetMapping("/basic/login/queryLoginInfo/{start}/{limit}")
    public MultiResultResponse<LoginInfoVo> queryLoginInfo(@ModelAttribute LoginInfoQueryVo loginInfoQueryVo,
                                                           @PathVariable("start") int start,
                                                           @PathVariable("limit") int limit) {
        LoginInfoQueryDto loginInfoQueryDto = new LoginInfoQueryDto();
        BeanUtils.copyProperties(loginInfoQueryVo,loginInfoQueryDto);
        PageFilter pageFilter = PageFilter.of(start,limit);
        List<LoginInfo> list = loginInfoService.queryLoginInfo(loginInfoQueryDto,pageFilter);
        List<LoginInfoVo> results = ConvertUtil.toNList(list,LoginInfo.class,LoginInfoVo.class);
        return new MultiResultResponse<>(results,pageFilter.getTotal());
    }

    @GetMapping("/basic/login/logOffUser/{userName}/{loginStatus}")
    public void logOffUser(@PathVariable("userName") String userName,
                           @PathVariable("loginStatus") String loginStatus) {
        this.loginInfoService.logOffUserByUserName(userName,loginStatus);

    }

    @GetMapping("/basic/login/getMainResource/{parentId}/{userName}")
    public List<ResourceVo> getMainResource(@PathVariable("parentId") String parentId, @PathVariable("userName") String userName) {
        Resource[]   resources = this.mainService.getResourcesByParentId(parentId,userName);
        List<ResourceVo> list = new LinkedList<>();
        for(Resource resource : resources){
            ResourceVo resourceVo = new ResourceVo();
            BeanUtils.copyProperties(resource,resourceVo);
            list.add(resourceVo);
        }
        return list;
    }
}
