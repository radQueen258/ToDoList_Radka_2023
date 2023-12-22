package Models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Task {
    private long TaskId;
    private long UserId;
    private long FileId;
    private String TaskName;
    private String TaskDescription;
    private Date TaskDeadline;
    private String TaskStatus;
}
