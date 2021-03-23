package rest.course.app.model;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	
	private final UUID userUid;
	
	@NotNull(message = "first name cannot be null")
	private final String firstName;
	
	@NotNull(message = "last name cannot be null")
	private final String lastName;
	
	@NotNull(message = "gender cannot be null")
	private final Gender gender;
	
	@NotNull(message = "age cannot be null")
	@Max(value =120)
	@Min(value = 18)
	private final Integer age;
	
	@Email
	@NotNull(message = "email cannot be null")
	private final String email;
	

	public User(@JsonProperty("userUid")UUID userUid, 
			@JsonProperty("firstName")String firstName,
			@JsonProperty("lastName") String lastName,
			@JsonProperty("gender")Gender gender,
			@JsonProperty("age")Integer age, 
			@JsonProperty("email")String email) {		
		this.userUid = userUid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.age = age;
		this.email = email;
	}

	//@JsonProperty("id")
	public UUID getUserUid() {
		return userUid;
	}

	// The response will not include fields marked by @JsonIgnore:
	//@JsonIgnore
	public String getFirstName() {
		return firstName;
	}

	//@JsonIgnore
	public String getLastName() {
		return lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public Integer getAge() {
		return age;
	}

	public String getEmail() {
		return email;
	}
	
	//Computed Json property
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	public int getYearOfBirth() {
		return LocalDate.now().minusYears(age).getYear();
	}
	
	public static User newUser(UUID userUid, User user) {
		return new User(userUid, user.getFirstName(), user.lastName, user.getGender(), 
				user.getAge(), user.getEmail());
				
	}

	@Override
	public String toString() {
		return "User [userUid=" + userUid + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
				+ ", age=" + age + ", email=" + email + "]";
	}

	
	
}
