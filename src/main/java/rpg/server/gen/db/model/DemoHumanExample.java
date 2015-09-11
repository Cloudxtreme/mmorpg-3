package rpg.server.gen.db.model;

import java.util.ArrayList;
import java.util.List;

public class DemoHumanExample {
    
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;
	
    public  DemoHumanExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }
	
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

	    public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

	    public Criteria andServeridIsNull() {
            addCriterion("serverId is null");
            return (Criteria) this;
        }

        public Criteria andServeridIsNotNull() {
            addCriterion("serverId is not null");
            return (Criteria) this;
        }

        public Criteria andServeridEqualTo(Integer value) {
            addCriterion("serverId =", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServeridNotEqualTo(Integer value) {
            addCriterion("serverId <>", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServeridGreaterThan(Integer value) {
            addCriterion("serverId >", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServeridGreaterThanOrEqualTo(Integer value) {
            addCriterion("serverId >=", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServeridLessThan(Integer value) {
            addCriterion("serverId <", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServeridLessThanOrEqualTo(Integer value) {
            addCriterion("serverId <=", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServeridIn(List<Integer> values) {
            addCriterion("serverId in", values, "serverId");
            return (Criteria) this;
        }

        public Criteria andServeridNotIn(List<Integer> values) {
            addCriterion("serverId not in", values, "serverId");
            return (Criteria) this;
        }

        public Criteria andServeridBetween(Integer value1, Integer value2) {
            addCriterion("serverId between", value1, value2, "serverId");
            return (Criteria) this;
        }

        public Criteria andServeridNotBetween(Integer value1, Integer value2) {
            addCriterion("serverId not between", value1, value2, "serverId");
            return (Criteria) this;
        }

	    public Criteria andAccountIsNull() {
            addCriterion("account is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("account is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(String value) {
            addCriterion("account =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(String value) {
            addCriterion("account <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(String value) {
            addCriterion("account >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(String value) {
            addCriterion("account >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(String value) {
            addCriterion("account <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(String value) {
            addCriterion("account <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<String> values) {
            addCriterion("account in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<String> values) {
            addCriterion("account not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(String value1, String value2) {
            addCriterion("account between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(String value1, String value2) {
            addCriterion("account not between", value1, value2, "account");
            return (Criteria) this;
        }

	    public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

	    public Criteria andProfessionIsNull() {
            addCriterion("profession is null");
            return (Criteria) this;
        }

        public Criteria andProfessionIsNotNull() {
            addCriterion("profession is not null");
            return (Criteria) this;
        }

        public Criteria andProfessionEqualTo(Integer value) {
            addCriterion("profession =", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionNotEqualTo(Integer value) {
            addCriterion("profession <>", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionGreaterThan(Integer value) {
            addCriterion("profession >", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionGreaterThanOrEqualTo(Integer value) {
            addCriterion("profession >=", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionLessThan(Integer value) {
            addCriterion("profession <", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionLessThanOrEqualTo(Integer value) {
            addCriterion("profession <=", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionIn(List<Integer> values) {
            addCriterion("profession in", values, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionNotIn(List<Integer> values) {
            addCriterion("profession not in", values, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionBetween(Integer value1, Integer value2) {
            addCriterion("profession between", value1, value2, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionNotBetween(Integer value1, Integer value2) {
            addCriterion("profession not between", value1, value2, "profession");
            return (Criteria) this;
        }

	    public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(Integer value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(Integer value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(Integer value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(Integer value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(Integer value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(Integer value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<Integer> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<Integer> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(Integer value1, Integer value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(Integer value1, Integer value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

	    public Criteria andTimeseconlineIsNull() {
            addCriterion("timeSecOnline is null");
            return (Criteria) this;
        }

        public Criteria andTimeseconlineIsNotNull() {
            addCriterion("timeSecOnline is not null");
            return (Criteria) this;
        }

        public Criteria andTimeseconlineEqualTo(Integer value) {
            addCriterion("timeSecOnline =", value, "timeSecOnline");
            return (Criteria) this;
        }

        public Criteria andTimeseconlineNotEqualTo(Integer value) {
            addCriterion("timeSecOnline <>", value, "timeSecOnline");
            return (Criteria) this;
        }

        public Criteria andTimeseconlineGreaterThan(Integer value) {
            addCriterion("timeSecOnline >", value, "timeSecOnline");
            return (Criteria) this;
        }

        public Criteria andTimeseconlineGreaterThanOrEqualTo(Integer value) {
            addCriterion("timeSecOnline >=", value, "timeSecOnline");
            return (Criteria) this;
        }

        public Criteria andTimeseconlineLessThan(Integer value) {
            addCriterion("timeSecOnline <", value, "timeSecOnline");
            return (Criteria) this;
        }

        public Criteria andTimeseconlineLessThanOrEqualTo(Integer value) {
            addCriterion("timeSecOnline <=", value, "timeSecOnline");
            return (Criteria) this;
        }

        public Criteria andTimeseconlineIn(List<Integer> values) {
            addCriterion("timeSecOnline in", values, "timeSecOnline");
            return (Criteria) this;
        }

        public Criteria andTimeseconlineNotIn(List<Integer> values) {
            addCriterion("timeSecOnline not in", values, "timeSecOnline");
            return (Criteria) this;
        }

        public Criteria andTimeseconlineBetween(Integer value1, Integer value2) {
            addCriterion("timeSecOnline between", value1, value2, "timeSecOnline");
            return (Criteria) this;
        }

        public Criteria andTimeseconlineNotBetween(Integer value1, Integer value2) {
            addCriterion("timeSecOnline not between", value1, value2, "timeSecOnline");
            return (Criteria) this;
        }

	    public Criteria andTimeloginIsNull() {
            addCriterion("timeLogin is null");
            return (Criteria) this;
        }

        public Criteria andTimeloginIsNotNull() {
            addCriterion("timeLogin is not null");
            return (Criteria) this;
        }

        public Criteria andTimeloginEqualTo(Long value) {
            addCriterion("timeLogin =", value, "timeLogin");
            return (Criteria) this;
        }

        public Criteria andTimeloginNotEqualTo(Long value) {
            addCriterion("timeLogin <>", value, "timeLogin");
            return (Criteria) this;
        }

        public Criteria andTimeloginGreaterThan(Long value) {
            addCriterion("timeLogin >", value, "timeLogin");
            return (Criteria) this;
        }

        public Criteria andTimeloginGreaterThanOrEqualTo(Long value) {
            addCriterion("timeLogin >=", value, "timeLogin");
            return (Criteria) this;
        }

        public Criteria andTimeloginLessThan(Long value) {
            addCriterion("timeLogin <", value, "timeLogin");
            return (Criteria) this;
        }

        public Criteria andTimeloginLessThanOrEqualTo(Long value) {
            addCriterion("timeLogin <=", value, "timeLogin");
            return (Criteria) this;
        }

        public Criteria andTimeloginIn(List<Long> values) {
            addCriterion("timeLogin in", values, "timeLogin");
            return (Criteria) this;
        }

        public Criteria andTimeloginNotIn(List<Long> values) {
            addCriterion("timeLogin not in", values, "timeLogin");
            return (Criteria) this;
        }

        public Criteria andTimeloginBetween(Long value1, Long value2) {
            addCriterion("timeLogin between", value1, value2, "timeLogin");
            return (Criteria) this;
        }

        public Criteria andTimeloginNotBetween(Long value1, Long value2) {
            addCriterion("timeLogin not between", value1, value2, "timeLogin");
            return (Criteria) this;
        }

	    public Criteria andTimelogoutIsNull() {
            addCriterion("timeLogout is null");
            return (Criteria) this;
        }

        public Criteria andTimelogoutIsNotNull() {
            addCriterion("timeLogout is not null");
            return (Criteria) this;
        }

        public Criteria andTimelogoutEqualTo(Long value) {
            addCriterion("timeLogout =", value, "timeLogout");
            return (Criteria) this;
        }

        public Criteria andTimelogoutNotEqualTo(Long value) {
            addCriterion("timeLogout <>", value, "timeLogout");
            return (Criteria) this;
        }

        public Criteria andTimelogoutGreaterThan(Long value) {
            addCriterion("timeLogout >", value, "timeLogout");
            return (Criteria) this;
        }

        public Criteria andTimelogoutGreaterThanOrEqualTo(Long value) {
            addCriterion("timeLogout >=", value, "timeLogout");
            return (Criteria) this;
        }

        public Criteria andTimelogoutLessThan(Long value) {
            addCriterion("timeLogout <", value, "timeLogout");
            return (Criteria) this;
        }

        public Criteria andTimelogoutLessThanOrEqualTo(Long value) {
            addCriterion("timeLogout <=", value, "timeLogout");
            return (Criteria) this;
        }

        public Criteria andTimelogoutIn(List<Long> values) {
            addCriterion("timeLogout in", values, "timeLogout");
            return (Criteria) this;
        }

        public Criteria andTimelogoutNotIn(List<Long> values) {
            addCriterion("timeLogout not in", values, "timeLogout");
            return (Criteria) this;
        }

        public Criteria andTimelogoutBetween(Long value1, Long value2) {
            addCriterion("timeLogout between", value1, value2, "timeLogout");
            return (Criteria) this;
        }

        public Criteria andTimelogoutNotBetween(Long value1, Long value2) {
            addCriterion("timeLogout not between", value1, value2, "timeLogout");
            return (Criteria) this;
        }

	    public Criteria andTimecreateIsNull() {
            addCriterion("timeCreate is null");
            return (Criteria) this;
        }

        public Criteria andTimecreateIsNotNull() {
            addCriterion("timeCreate is not null");
            return (Criteria) this;
        }

        public Criteria andTimecreateEqualTo(Long value) {
            addCriterion("timeCreate =", value, "timeCreate");
            return (Criteria) this;
        }

        public Criteria andTimecreateNotEqualTo(Long value) {
            addCriterion("timeCreate <>", value, "timeCreate");
            return (Criteria) this;
        }

        public Criteria andTimecreateGreaterThan(Long value) {
            addCriterion("timeCreate >", value, "timeCreate");
            return (Criteria) this;
        }

        public Criteria andTimecreateGreaterThanOrEqualTo(Long value) {
            addCriterion("timeCreate >=", value, "timeCreate");
            return (Criteria) this;
        }

        public Criteria andTimecreateLessThan(Long value) {
            addCriterion("timeCreate <", value, "timeCreate");
            return (Criteria) this;
        }

        public Criteria andTimecreateLessThanOrEqualTo(Long value) {
            addCriterion("timeCreate <=", value, "timeCreate");
            return (Criteria) this;
        }

        public Criteria andTimecreateIn(List<Long> values) {
            addCriterion("timeCreate in", values, "timeCreate");
            return (Criteria) this;
        }

        public Criteria andTimecreateNotIn(List<Long> values) {
            addCriterion("timeCreate not in", values, "timeCreate");
            return (Criteria) this;
        }

        public Criteria andTimecreateBetween(Long value1, Long value2) {
            addCriterion("timeCreate between", value1, value2, "timeCreate");
            return (Criteria) this;
        }

        public Criteria andTimecreateNotBetween(Long value1, Long value2) {
            addCriterion("timeCreate not between", value1, value2, "timeCreate");
            return (Criteria) this;
        }

	    public Criteria andExpcurIsNull() {
            addCriterion("expCur is null");
            return (Criteria) this;
        }

        public Criteria andExpcurIsNotNull() {
            addCriterion("expCur is not null");
            return (Criteria) this;
        }

        public Criteria andExpcurEqualTo(Long value) {
            addCriterion("expCur =", value, "expCur");
            return (Criteria) this;
        }

        public Criteria andExpcurNotEqualTo(Long value) {
            addCriterion("expCur <>", value, "expCur");
            return (Criteria) this;
        }

        public Criteria andExpcurGreaterThan(Long value) {
            addCriterion("expCur >", value, "expCur");
            return (Criteria) this;
        }

        public Criteria andExpcurGreaterThanOrEqualTo(Long value) {
            addCriterion("expCur >=", value, "expCur");
            return (Criteria) this;
        }

        public Criteria andExpcurLessThan(Long value) {
            addCriterion("expCur <", value, "expCur");
            return (Criteria) this;
        }

        public Criteria andExpcurLessThanOrEqualTo(Long value) {
            addCriterion("expCur <=", value, "expCur");
            return (Criteria) this;
        }

        public Criteria andExpcurIn(List<Long> values) {
            addCriterion("expCur in", values, "expCur");
            return (Criteria) this;
        }

        public Criteria andExpcurNotIn(List<Long> values) {
            addCriterion("expCur not in", values, "expCur");
            return (Criteria) this;
        }

        public Criteria andExpcurBetween(Long value1, Long value2) {
            addCriterion("expCur between", value1, value2, "expCur");
            return (Criteria) this;
        }

        public Criteria andExpcurNotBetween(Long value1, Long value2) {
            addCriterion("expCur not between", value1, value2, "expCur");
            return (Criteria) this;
        }

	    public Criteria andLevelIsNull() {
            addCriterion("level is null");
            return (Criteria) this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("level is not null");
            return (Criteria) this;
        }

        public Criteria andLevelEqualTo(Integer value) {
            addCriterion("level =", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotEqualTo(Integer value) {
            addCriterion("level <>", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThan(Integer value) {
            addCriterion("level >", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("level >=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThan(Integer value) {
            addCriterion("level <", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThanOrEqualTo(Integer value) {
            addCriterion("level <=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelIn(List<Integer> values) {
            addCriterion("level in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotIn(List<Integer> values) {
            addCriterion("level not in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelBetween(Integer value1, Integer value2) {
            addCriterion("level between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("level not between", value1, value2, "level");
            return (Criteria) this;
        }

	    public Criteria andHpcurIsNull() {
            addCriterion("hpCur is null");
            return (Criteria) this;
        }

        public Criteria andHpcurIsNotNull() {
            addCriterion("hpCur is not null");
            return (Criteria) this;
        }

        public Criteria andHpcurEqualTo(Integer value) {
            addCriterion("hpCur =", value, "hpCur");
            return (Criteria) this;
        }

        public Criteria andHpcurNotEqualTo(Integer value) {
            addCriterion("hpCur <>", value, "hpCur");
            return (Criteria) this;
        }

        public Criteria andHpcurGreaterThan(Integer value) {
            addCriterion("hpCur >", value, "hpCur");
            return (Criteria) this;
        }

        public Criteria andHpcurGreaterThanOrEqualTo(Integer value) {
            addCriterion("hpCur >=", value, "hpCur");
            return (Criteria) this;
        }

        public Criteria andHpcurLessThan(Integer value) {
            addCriterion("hpCur <", value, "hpCur");
            return (Criteria) this;
        }

        public Criteria andHpcurLessThanOrEqualTo(Integer value) {
            addCriterion("hpCur <=", value, "hpCur");
            return (Criteria) this;
        }

        public Criteria andHpcurIn(List<Integer> values) {
            addCriterion("hpCur in", values, "hpCur");
            return (Criteria) this;
        }

        public Criteria andHpcurNotIn(List<Integer> values) {
            addCriterion("hpCur not in", values, "hpCur");
            return (Criteria) this;
        }

        public Criteria andHpcurBetween(Integer value1, Integer value2) {
            addCriterion("hpCur between", value1, value2, "hpCur");
            return (Criteria) this;
        }

        public Criteria andHpcurNotBetween(Integer value1, Integer value2) {
            addCriterion("hpCur not between", value1, value2, "hpCur");
            return (Criteria) this;
        }

	    public Criteria andMpcurIsNull() {
            addCriterion("mpCur is null");
            return (Criteria) this;
        }

        public Criteria andMpcurIsNotNull() {
            addCriterion("mpCur is not null");
            return (Criteria) this;
        }

        public Criteria andMpcurEqualTo(Integer value) {
            addCriterion("mpCur =", value, "mpCur");
            return (Criteria) this;
        }

        public Criteria andMpcurNotEqualTo(Integer value) {
            addCriterion("mpCur <>", value, "mpCur");
            return (Criteria) this;
        }

        public Criteria andMpcurGreaterThan(Integer value) {
            addCriterion("mpCur >", value, "mpCur");
            return (Criteria) this;
        }

        public Criteria andMpcurGreaterThanOrEqualTo(Integer value) {
            addCriterion("mpCur >=", value, "mpCur");
            return (Criteria) this;
        }

        public Criteria andMpcurLessThan(Integer value) {
            addCriterion("mpCur <", value, "mpCur");
            return (Criteria) this;
        }

        public Criteria andMpcurLessThanOrEqualTo(Integer value) {
            addCriterion("mpCur <=", value, "mpCur");
            return (Criteria) this;
        }

        public Criteria andMpcurIn(List<Integer> values) {
            addCriterion("mpCur in", values, "mpCur");
            return (Criteria) this;
        }

        public Criteria andMpcurNotIn(List<Integer> values) {
            addCriterion("mpCur not in", values, "mpCur");
            return (Criteria) this;
        }

        public Criteria andMpcurBetween(Integer value1, Integer value2) {
            addCriterion("mpCur between", value1, value2, "mpCur");
            return (Criteria) this;
        }

        public Criteria andMpcurNotBetween(Integer value1, Integer value2) {
            addCriterion("mpCur not between", value1, value2, "mpCur");
            return (Criteria) this;
        }

    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}