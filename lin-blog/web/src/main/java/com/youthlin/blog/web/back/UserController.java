package com.youthlin.blog.web.back;

import com.google.common.collect.Maps;
import com.youthlin.blog.model.bo.Page;
import com.youthlin.blog.model.enums.Role;
import com.youthlin.blog.model.po.User;
import com.youthlin.blog.model.po.UserMeta;
import com.youthlin.blog.service.UserService;
import com.youthlin.blog.util.Constant;
import com.youthlin.blog.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： lin
 * 时间： 2017-05-07 14:56
 */
@Controller
@RequestMapping("/admin")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;

    @RequestMapping("/users/all")
    public String allUsers(Model model, HttpServletRequest request) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Administrator.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        model.addAttribute("title", __("All Users"));
        String pageStr = request.getParameter("page");
        int pageIndex = 1;
        try {
            pageIndex = Integer.parseInt(pageStr);
        } catch (NumberFormatException ignore) {
        }
        Page<User> userPage = userService.listByPage(pageIndex, Constant.DEFAULT_PAGE_SIZE);
        model.addAttribute("userPage", userPage);
        List<UserMeta> allRole = userService.getAllRole();
        Map<Long, UserMeta> roleMetaMap = Maps.newHashMap();
        for (UserMeta userMeta : allRole) {
            roleMetaMap.put(userMeta.getUserId(), userMeta);
        }
        model.addAttribute("roleMetaMap", roleMetaMap);
        return "admin/users-all";
    }

    private String editPage(Model model, HttpServletRequest request) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Administrator.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        model.addAttribute("title", __("Edit User"));
        String idStr = request.getParameter("id");
        long id = -1;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException ignore) {
        }
        if (id < 0) {
            model.addAttribute(Constant.ERROR, __("Invalid param."));
        }
        User user = userService.findById(id);
        model.addAttribute("userEdit", user);
        UserMeta roleMeta = userService.findMetaByUserIdAndMetaKey(user.getUserId(), Constant.K_ROLE);
        model.addAttribute("roleMeta", roleMeta);
        return "admin/users-edit";
    }

    @RequestMapping(path = "/users/edit", method = RequestMethod.GET)
    public String editUser(Model model, HttpServletRequest request) {
        return editPage(model, request);
    }

    @RequestMapping(path = "/users/edit", method = RequestMethod.POST)
    public String editUser(@RequestParam Map<String, String> params, Model model, HttpServletRequest request) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Administrator.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        String idStr = params.get("id");
        long id = -1;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException ignore) {
        }
        if (id < 0) {
            model.addAttribute(Constant.ERROR, __("Invalid param"));
            log.info("参数错误 id < 0");
            return editPage(model, request);
        }
        User user = userService.findById(id);
        String roleStr = params.get("role");
        role = Role.nameOf(roleStr);
        User currentUser = (User) request.getAttribute(Constant.USER);
        UserMeta roleMeta = userService.findMetaByUserIdAndMetaKey(user.getUserId(), Constant.K_ROLE);
        if (role != null && !user.getUserId().equals(currentUser.getUserId()) && !user.getUserId().equals(1L)) {
            //该用户不是自己，才可以修改他的角色; 而且要修改的不是初始管理员
            roleMeta.setMetaValue(role.name());
            userService.updateMeta(roleMeta);
        }

        String email = params.get("email");
        String url = params.get("url");
        String name = params.get("name");
        String newPass = params.get("newPass");
        if (!StringUtils.hasText(email) || !StringUtils.hasText(name)) {
            model.addAttribute(Constant.ERROR, __("Email and display name are required."));
            log.info("email name  是必填的.");
            return editPage(model, request);
        }
        if (StringUtils.hasText(url)) {
            try {
                //noinspection ResultOfMethodCallIgnored
                URI.create(url).toURL();
            } catch (MalformedURLException e) {
                model.addAttribute(Constant.ERROR, __("illegal url."));
                log.info("illegal url: {}", url, e);
                return editPage(model, request);
            }
        }
        user.setUserEmail(email)
                .setUserUrl(url)
                .setDisplayName(name);
        if (StringUtils.hasText(newPass)) {
            String rand = UUID.randomUUID().toString().substring(0, Constant.RAND_LEN);
            newPass = rand + MD5Util.md5(rand + newPass);
            user.setUserPass(newPass);
            UserMeta loginInfoMeta = userService.findMetaByUserIdAndMetaKey(user.getUserId(), Constant.K_LOGIN_INFO);
            loginInfoMeta.setMetaValue(null);
            userService.updateMeta(loginInfoMeta);
        }
        userService.update(user);
        return "redirect:/admin/users/all";
    }

    @RequestMapping(path = "/users/add", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Administrator.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        model.addAttribute("title", __("Add User"));
        return "admin/users-add";
    }

    @RequestMapping(path = "/users/add", method = RequestMethod.POST)
    public String add(@RequestParam Map<String, String> params, Model model, HttpServletRequest request) {
        Role role1 = (Role) request.getAttribute(Constant.K_ROLE);
        if (role1 != null && role1.getCode() < Role.Administrator.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        model.addAttribute("title", __("Add User"));
        log.debug("add user, params = {}", params);
        String username = params.get("username");
        String email = params.get("email");
        String pass = params.get("password");
        String role = params.get("role");
        User byUserName = userService.findByUserName(username);
        if (byUserName != null) {
            model.addAttribute(Constant.ERROR, __("This username has been used."));
            return "admin/users-add";
        }
        if (!StringUtils.hasText(pass) || pass.length() != Constant.MD5_LEN) {
            model.addAttribute(Constant.ERROR, __("Password is required."));
            return "admin/users-add";
        }
        if (!email.matches(".*?@.*?\\..*?")) {
            model.addAttribute(Constant.ERROR, __("Illegal email."));
            return "admin/users-add";
        }
        String rand = UUID.randomUUID().toString().substring(0, Constant.RAND_LEN);
        pass = rand + MD5Util.md5(rand + pass);
        User user = new User();
        user.setUserLogin(username)
                .setUserEmail(email)
                .setUserPass(pass)
                .setDisplayName(username)
        ;
        Role userRole = Role.nameOf(role);
        if (userRole == null) {
            userRole = Role.Author;
        }
        userService.saveNewUser(user, userRole);
        log.info("saved new user {}", user);
        model.addAttribute(Constant.MSG, __("User added."));
        return "admin/users-add";
    }

    @RequestMapping(path = "/users/my", method = RequestMethod.GET)
    public String profile(Model model) {
        model.addAttribute("title", __("My Profile"));
        return "admin/users-profile";
    }

    @RequestMapping(path = {"/users/my"}, method = {RequestMethod.POST})
    public String updateProfile(@RequestParam Map<String, String> params, HttpServletRequest request, Model model) {
        model.addAttribute("title", __("My Profile"));
        String email = params.get("email");
        String name = params.get("name");
        String url = params.get("url");
        String oldPass = params.get("oldPass");
        String newPass = params.get("newPass");
        if (!StringUtils.hasText(email) || !StringUtils.hasText(name)) {
            model.addAttribute(Constant.ERROR, __("Email and display name are required."));
            return "admin/users-profile";
        }
        if (StringUtils.hasText(url)) {
            try {
                //noinspection ResultOfMethodCallIgnored
                URI.create(url).toURL();
            } catch (MalformedURLException e) {
                model.addAttribute(Constant.ERROR, __("illegal url."));
                return "admin/users-profile";
            }
        }
        User user = (User) request.getAttribute(Constant.USER);
        if (StringUtils.hasText(oldPass) && StringUtils.hasText(newPass)) {
            if (oldPass.length() != Constant.MD5_LEN || newPass.length() != Constant.MD5_LEN) {
                model.addAttribute(Constant.ERROR, __("It seem that JavaScript doesn't work, but we need it to generate password."));
                return "admin/users-profile";
            }
            String userPass = user.getUserPass();
            String rand = userPass.substring(0, Constant.RAND_LEN);
            userPass = rand + MD5Util.md5(rand + oldPass);
            if (!userPass.equalsIgnoreCase(user.getUserPass())) {
                model.addAttribute(Constant.ERROR, __("Password is incorrect."));
                return "admin/users-profile";
            }
            rand = UUID.randomUUID().toString().substring(0, Constant.RAND_LEN);
            newPass = rand + MD5Util.md5(rand + newPass);
            user.setUserPass(newPass);
        }
        user.setUserEmail(email);
        user.setDisplayName(name);
        user.setUserUrl(url);
        userService.update(user);
        model.addAttribute(Constant.MSG, __("Your profile has been updated."));
        return "admin/users-profile";
    }

}
