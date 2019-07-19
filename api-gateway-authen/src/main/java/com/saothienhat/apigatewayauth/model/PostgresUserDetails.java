package com.saothienhat.apigatewayauth.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Binh Trinh
 *
 */
@JsonDeserialize(as = PostgresUserDetails.class)
public class PostgresUserDetails implements UserDetails {
    private String username;
    private String password;
    private Integer active;
    private boolean isLocked;
    private boolean isExpired;
    private boolean isEnabled;
    private List<GrantedAuthority> grantedAuthorities;

    public PostgresUserDetails(String username, String password,Integer active, boolean isLocked, boolean isExpired, boolean isEnabled, String [] authorities) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.isLocked = isLocked;
        this.isExpired = isExpired;
        this.isEnabled = isEnabled;
        this.grantedAuthorities = AuthorityUtils.createAuthorityList(authorities);
    }

    public PostgresUserDetails(String username,  String [] authorities) {
        this.username = username;
        this.grantedAuthorities = AuthorityUtils.createAuthorityList(authorities);
    }

    public PostgresUserDetails() {
        super();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active==1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public void setUsername(String username) {
		this.username = username;
	}

	
	

	@Override
		public String toString() {
			StringBuffer rolesBF = new StringBuffer();
			rolesBF.append("[ ");
			for (GrantedAuthority grantedAuthority : this.getGrantedAuthorities()) {
				rolesBF.append(grantedAuthority.getAuthority() + ", ");
			}
			rolesBF.append(" ]");
			String text = "username: " + this.getUsername()
				+ " + active: " + this.active
				+ " + isLocked: " + this.isLocked
				+ " + isExpired: " + this.isExpired
				+ " + isEnabled: " + this.isEnabled
				+ " + Roles: " + rolesBF.toString();
			return text;
		}

	public List<GrantedAuthority> getGrantedAuthorities() {
		return grantedAuthorities;
	}
}
