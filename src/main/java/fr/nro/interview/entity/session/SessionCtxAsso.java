package fr.nro.interview.entity.session;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SessionCtxAsso implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 2958323078294007479L;

  @Column(name = "question_id")
  private Long questionId;
  
  @Column(name = "student_ctx_id")
  private Long studentCtxId;

  public Long getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Long questionId) {
    this.questionId = questionId;
  }

  public Long getStudentCtxId() {
    return studentCtxId;
  }

  public void setStudentCtxId(Long studentCtxId) {
    this.studentCtxId = studentCtxId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((questionId == null) ? 0 : questionId.hashCode());
    result = prime * result + ((studentCtxId == null) ? 0 : studentCtxId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SessionCtxAsso other = (SessionCtxAsso) obj;
    if (questionId == null) {
      if (other.questionId != null)
        return false;
    } else if (!questionId.equals(other.questionId))
      return false;
    if (studentCtxId == null) {
      if (other.studentCtxId != null)
        return false;
    } else if (!studentCtxId.equals(other.studentCtxId))
      return false;
    return true;
  }
  
  
}
