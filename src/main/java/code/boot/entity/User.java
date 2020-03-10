package code.boot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = -5456849407222529459L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@NotNull
	@Size(max = 100)
	@Email
	@Column(unique = true)
	private String email;

	@Size(min = 6, max = 100)
	@JsonIgnore
	private String password;

	@NotNull
	@Size(min = 3, max = 100)
	@Column(name = "first_name")
	private String firstName;

	@NotNull
	@Size(min = 3, max = 100)
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "profession")
	private String profession;

	@Lob
	@Column(name = "profile_image", columnDefinition = "mediumblob")
	private byte[] profileImage;

	@NotNull
	@Column(unique = true)
	@Digits(integer = 10, fraction = 0)
	private long mobile;

	public User() {
	}

	public User(@NotNull @Size(max = 100) @Email String email, @Size(min = 6, max = 100) String password,
			@NotNull @Size(min = 3, max = 100) String firstName, @NotNull @Size(min = 3, max = 100) String lastName,
			String profession, @NotNull @Digits(integer = 10, fraction = 0) long mobile) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profession = profession;
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public byte[] getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

}
