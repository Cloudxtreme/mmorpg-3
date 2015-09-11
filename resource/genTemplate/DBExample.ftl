package ${modelPackage};

import java.util.ArrayList;
import java.util.List;

public class ${objName}Example {
    
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;
	
    public  ${objName}Example() {
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

       <#list columnList as column>
	    public Criteria and${column.camelCaseName}IsNull() {
            addCriterion("${alias}${column.name} is null");
            return (Criteria) this;
        }

        public Criteria and${column.camelCaseName}IsNotNull() {
            addCriterion("${alias}${column.name} is not null");
            return (Criteria) this;
        }

        public Criteria and${column.camelCaseName}EqualTo(${column.javaType} value) {
            addCriterion("${alias}${column.name} =", value, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.camelCaseName}NotEqualTo(${column.javaType} value) {
            addCriterion("${alias}${column.name} <>", value, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.camelCaseName}GreaterThan(${column.javaType} value) {
            addCriterion("${alias}${column.name} >", value, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.camelCaseName}GreaterThanOrEqualTo(${column.javaType} value) {
            addCriterion("${alias}${column.name} >=", value, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.camelCaseName}LessThan(${column.javaType} value) {
            addCriterion("${alias}${column.name} <", value, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.camelCaseName}LessThanOrEqualTo(${column.javaType} value) {
            addCriterion("${alias}${column.name} <=", value, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.camelCaseName}In(List<${column.javaType}> values) {
            addCriterion("${alias}${column.name} in", values, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.camelCaseName}NotIn(List<${column.javaType}> values) {
            addCriterion("${alias}${column.name} not in", values, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.camelCaseName}Between(${column.javaType} value1, ${column.javaType} value2) {
            addCriterion("${alias}${column.name} between", value1, value2, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.camelCaseName}NotBetween(${column.javaType} value1, ${column.javaType} value2) {
            addCriterion("${alias}${column.name} not between", value1, value2, "${column.name}");
            return (Criteria) this;
        }

		<#if column.stringColumn>
		public Criteria and${column.camelCaseName}Like(${column.javaType} value) {
            addCriterion("${alias}${column.name} like", value, ""${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.camelCaseName}NotLike(${column.javaType} value) {
            addCriterion("${alias}${column.name} not like", value, ""${column.name}");
            return (Criteria) this;
        }
		</#if>
	   </#list>
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