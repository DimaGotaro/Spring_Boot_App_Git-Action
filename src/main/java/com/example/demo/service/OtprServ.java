package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.other.StatM;
import com.example.demo.repository.UserRepo;
import com.example.demo.repository.UserRepo2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class OtprServ {

    private UserRepo userRepo;
    private UserRepo2 userRepo2;

    public OtprServ(UserRepo userRepo, UserRepo2 userRepo2) {
        this.userRepo = userRepo;
        this.userRepo2 = userRepo2;
    }

    public String otpr(Model model, String res, User user) {

        List<User> asc = userRepo.findAllByIdIsNotNullOrderByIdAsc();
        userRepo.findById(user.getId()).ifPresent(asc::remove);

        model.addAttribute("users", asc);
        model.addAttribute("res", res);

        return "otpr";
    }

    public String otpr2(String per, Long[] usr, User user, Model model) {

        if (!per.equals("0") && usr.length > 1 && StatM.itsNumber(per) && StatM.prov(per)) {
            HashSet<User> usr1 = new HashSet<>();
            if (usr.length > 10) {
                HashMap<Long, User> map = new HashMap<>();
                for (User userM : userRepo2.findAll()) {
                    map.put(userM.getId(), userM);
                }
                Arrays.stream(usr).filter(x -> x != 0L).forEach(r -> usr1.add(map.get(r)));
            }
            else {
                Arrays.stream(usr).filter(x -> x != 0L)
                        .forEach(r -> usr1.add(userRepo.findById(r).orElse(null)));
            }
            User user1 = userRepo.findById(user.getId()).orElse(null);
            if (user1 != null && usr1.size() > 0 &&
                    user1.getM().compareTo(new BigDecimal(per).multiply(new BigDecimal(usr1.size()))) >= 0) {

                user1.setMoney(user1.getM().subtract(new BigDecimal(per).multiply(new BigDecimal(usr1.size()))));
                userRepo.save(user1);

                for (User f :
                        usr1) {
                    f.setMoney(f.getM().add(new BigDecimal(per)));
                    userRepo.save(f);
                }
                String res = "Операция выполнена успешно. Поздравляем!";
                model.addAttribute("res", res);
            }
            else {
                String res = "На вашем счету не достаточно средств. Пополните баланс.";
                model.addAttribute("res", res);
            }
        }
        else {
            String res = "Введите значение не равное 0, в формате 1111.11!";
            model.addAttribute("res", res);
        }

        return "redirect:/o/otpr";
    }
}
