package users;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import users.info.UserType;

import java.time.LocalDate;

import static com.google.common.base.Preconditions.checkArgument;
import static users.info.UserType.ADULT;
import static users.info.UserType.CHILD;

@ToString(exclude = "MAX_BIRTH_YEAR")
@EqualsAndHashCode
public abstract class User {
    private static final int ADULT_AGE = 18;
    private final long MAX_BIRTH_YEAR = LocalDate.now().getYear();

    @Setter
    @Getter
    protected int id;
    protected final String name;
    protected final String surname;
    private final int birthYear;
    @Getter
    protected UserType userType;

    protected User(@NotNull String name,
                   @NotNull String surname,
                   int birthYear) {
        this.name = name;
        this.surname = surname;
        checkArgument(birthYear > 1920 && birthYear < MAX_BIRTH_YEAR, "invalid birth year");
        this.birthYear = birthYear;
        this.userType = setUserType();
    }

    public int getAge() {
        return LocalDate.now().getYear() - birthYear;
    }

    private UserType setUserType() {
        return (getAge() >= ADULT_AGE) ? ADULT : CHILD;
    }
}
