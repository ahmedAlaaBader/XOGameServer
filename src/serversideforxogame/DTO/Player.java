/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xogameverone.DTO;

/**
 *
 * @author Hossam
 */
public class Player {
    
    String UserName;
    String Email;
    String Password;
    int Score;
    boolean Active;
    boolean InActive;

    public Player(String UserName, String Email, String Password, int Score, boolean Active, boolean InActive) {
        this.UserName = UserName;
        this.Email = Email;
        this.Password = Password;
        this.Score = Score;
        this.Active = Active;
        this.InActive = InActive;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean Active) {
        this.Active = Active;
    }

    public boolean isInActive() {
        return InActive;
    }

    public void setInActive(boolean InActive) {
        this.InActive = InActive;
    }
    
}
