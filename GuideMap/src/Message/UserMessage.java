package Message;

import Utils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
public class UserMessage {
    private String name;
    private ArrayList<String> likeSpot;
    private boolean flag;
    private String password;
    private int autologin;
    private int rememberpassword;
    private int Jurisdiction;


    public UserMessage() {
    }

    ;

    public int getJurisdiction() {
        return Jurisdiction;
    }

    public void setJurisdiction(int jurisdiction) {
        Jurisdiction = jurisdiction;
    }

    public UserMessage(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public int getAutologin() {
        return autologin;
    }

    public int getRememberpassword() {
        return rememberpassword;
    }

    public void setAutologin(int autologin) {
        this.autologin = autologin;
    }

    public void setRememberpassword(int rememberpassword) {
        this.rememberpassword = rememberpassword;
    }

    public void setLikeSpot(ArrayList<String> likeSpot) {
        this.likeSpot = likeSpot;
    }

    public ArrayList<String> getLikeSpot() {
        return likeSpot;
    }

    public String getName() {
        return name;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean setFlag(boolean flag) {
        this.flag = flag;
        return flag;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean setFlag() {
        try {
            Connection conn = DbUtils.getConnection();
            try (PreparedStatement preparedStatement = conn.prepareStatement("select * from user where user_name = ?")) {
                preparedStatement.setString(1, getName());
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    if (rs.getInt(4) == 1) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
                rs.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public String getPassword() {
        return password;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserMessage)) return false;
        final UserMessage other = (UserMessage) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$likeSpot = this.getLikeSpot();
        final Object other$likeSpot = other.getLikeSpot();
        if (this$likeSpot == null ? other$likeSpot != null : !this$likeSpot.equals(other$likeSpot)) return false;
        if (this.flag != other.flag) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        if (this.getAutologin() != other.getAutologin()) return false;
        if (this.getRememberpassword() != other.getRememberpassword()) return false;
        if (this.getJurisdiction() != other.getJurisdiction()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserMessage;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $likeSpot = this.getLikeSpot();
        result = result * PRIME + ($likeSpot == null ? 43 : $likeSpot.hashCode());
        result = result * PRIME + (this.flag ? 79 : 97);
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        result = result * PRIME + this.getAutologin();
        result = result * PRIME + this.getRememberpassword();
        result = result * PRIME + this.getJurisdiction();
        return result;
    }

    public String toString() {
        return "UserMessage(name=" + this.getName() + ", likeSpot=" + this.getLikeSpot() + ", flag=" + this.flag + ", password=" + this.getPassword() + ", autologin=" + this.getAutologin() + ", rememberpassword=" + this.getRememberpassword() + ", Jurisdiction=" + this.getJurisdiction() + ")";
    }
}
