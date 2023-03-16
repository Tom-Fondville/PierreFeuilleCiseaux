package com.epsi.pierrefeuilleciseaux.Controller;


import com.epsi.pierrefeuilleciseaux.model.Action;
import com.epsi.pierrefeuilleciseaux.model.Score;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {
    private Score score;

    public GameController() {
        this.score = new Score();
    }


    private String coupGagant(Action actionJoueur, Action actionRobot){
        if (actionJoueur == actionRobot) return "fait égalité";

        switch (actionJoueur){
            case CISEAUX :
                if(actionRobot == Action.FEUILLE) return "gagné";
                else return "perdu";
            case PIERRE :
                if (actionRobot == Action.CISEAUX) return "gagné";
                else return "perdu";
            case FEUILLE:
                if (actionRobot == Action.PIERRE)  return "gagné";
                else return "perdu";
        }
        return "";
    }

    @PostMapping(path = "play/{action}")
    public String play(@PathVariable Action action){
        Action actionRobot;
        var random = Math.random() *10;
        if (random <= 3){
            actionRobot = Action.CISEAUX;
        }else if (random <= 6){
            actionRobot = Action.FEUILLE;
        }else actionRobot = Action.PIERRE;

        var result = coupGagant(action, actionRobot);
        if (result == "gagné") score.win ++;
        if (result == "perdu") score.lose ++;
        if (result == "fait égalité") score.tie ++;

        var message = "Vous avez joué " + action + " l'ordinateur à joué " + actionRobot + ", vous avez " + result;
        return message;
    }

    @PostMapping(path = "/restart")
    public ResponseEntity<Score> restart(){
        this.score = new Score();
        return new ResponseEntity<Score>(score, HttpStatus.OK);
    }

    @GetMapping(path = "/score")
    public String getScore(){
        return "win:" +score.win + ",lose:" + score.lose + ",tie:" + score.tie;
    }

    @PutMapping(path = "/score/{win}/{lose}/{tie}")
    public void updateScore(@PathVariable int win, @PathVariable int lose, @PathVariable int tie){
        this.score.win = win;
        this.score.lose = lose;
        this.score.tie = tie;
    }


}
