package banana_project.util;

import lombok.Builder;
import lombok.Data;

@Data
// 접근자/생성자 자동 생성
// getter, setter 자동으로 사용가능(컴파일 시 추가된다)
//
@Builder
// 생성자를 자동으로 생성
public class DeptVO {
    int deptno;

    String dname;
    String loc;
    public int getDeptno() {
        return deptno;
    }
    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

    public String getDname() {
        return dname;
    }
    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLoc() {
        return loc;
    }
    public void setLoc(String loc) {
        this.loc = loc;
    }
}


