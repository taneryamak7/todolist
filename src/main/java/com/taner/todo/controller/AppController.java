package com.taner.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taner.todo.model.User;
import com.taner.todo.service.UserService;

@Controller
public class AppController {

	@Autowired
	private UserService service;

	@RequestMapping("/")
	public String viewHomePage(Model model, Authentication authResult) {

		String role = authResult.getAuthorities().toString();

		if (role.contains("USER")) {
			return "redirect:/list-todos";
		} else {
			List<User> listUsers = service.listAll();
			model.addAttribute("listUsers", listUsers);

			return "index";
		}

	}

	@RequestMapping("/new")
	public String showNewUserForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);

		return "new_user";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") User user) {

		User oldUser = null;
		if (user.getId() != null) {
			oldUser = service.get(user.getId());
			user.setPassword(oldUser.getPassword());
			user.setRole(oldUser.getRole());
		}

		service.save(user);

		return "redirect:/";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView showEditUserPage(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_user");
		User user = service.get(id);
		mav.addObject("user", user);

		return mav;
	}

	@RequestMapping("/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") int id) {
		service.delete(id);
		return "redirect:/";
	}
}
