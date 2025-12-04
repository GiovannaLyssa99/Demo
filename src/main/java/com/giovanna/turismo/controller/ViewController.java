package com.giovanna.turismo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }

    @GetMapping("/ponto/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        model.addAttribute("pontoId", id);
        return "detalhe";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registrar";
    }
}