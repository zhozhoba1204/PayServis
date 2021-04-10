package ru.blinov.payservis.model;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;
    private String userName;
    private String password;
    private BigDecimal totalBalance;
    private String sessionId;
    private Date blockedUntil = new Date(0);
    private Boolean blocked;



    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "USER_TRANSACTIONS", joinColumns = @JoinColumn(name = "USER_ID"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<Transaction> setOfTransactions;

    public User(){
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Date getBlockedUntil() {
        return blockedUntil;
    }

    public void setBlockedUntil(Date blockedUntil) {
        this.blockedUntil = blockedUntil;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public List<Transaction> getSetOfTransactions() {
        return setOfTransactions;
    }

    public void setSetOfTransactions(List<Transaction> setOfTransactions) {
        this.setOfTransactions = setOfTransactions;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password);
    }



}
