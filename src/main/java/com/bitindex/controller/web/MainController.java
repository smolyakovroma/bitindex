package com.bitindex.controller.web;

import com.bitindex.domain.dto.CoinDTO;
import com.bitindex.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class MainController {

//    @Autowired
//    private CoinService coinService;

    @RequestMapping(value = {"/", "/index"})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginPost(@RequestParam String pass) {
        ModelAndView modelAndView = new ModelAndView();

        if (pass.equals("Qwerty123")) {
            modelAndView.setViewName("admin");
        } else {
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;

    }



//    @RequestMapping(value = "/edit_coin/{id}", method = RequestMethod.GET)
//    public ModelAndView editCoin(@PathVariable int id) {
//        ModelAndView modelAndView = new ModelAndView();
//        Coin coin = coinService.findCoinById(id);
//        if (coin != null) {
//            modelAndView.addObject("coin", coin);
//            modelAndView.setViewName("edit_coin");
//        } else {
//            modelAndView.setViewName("redirect:/");
//        }
//        return modelAndView;
//    }


}
