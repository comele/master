package ec.com.doc.ele.enumeracion;

import org.hibernate.criterion.MatchMode;

public enum ComparatorTypeEnum {
	LIKE_EXACT_COMPARATOR (MatchMode.EXACT, Expression.LIKE_EXACT),
	LIKE_END_COMPARATOR (MatchMode.END, Expression.LIKE_END),
	LIKE_START_COMPARATOR (MatchMode.START, Expression.LIKE_START),
	LIKE_ANYWHERE_COMPARATOR (MatchMode.ANYWHERE, Expression.LIKE_ANYWHERE),
	INSENSITIVE_LIKE_EXACT_COMPARATOR (MatchMode.EXACT, Expression.LIKE_EXACT),
	INSENSITIVE_LIKE_END_COMPARATOR (MatchMode.END, Expression.LIKE_END),
	INSENSITIVE_LIKE_START_COMPARATOR (MatchMode.START, Expression.LIKE_START),
	INSENSITIVE_LIKE_ANYWHERE_COMPARATOR (MatchMode.ANYWHERE, Expression.LIKE_ANYWHERE),    
    EQUAL_COMPARATOR,
    GREATER_THAN_OR_EQUAL_COMPARATOR,
    GREATER_THAN_COMPARATOR,
    LESS_THAN_OR_EQUAL_COMPARATOR,
    LESS_THAN_COMPARATOR,
    IN_COMPARATOR,
    NOT_IN_COMPARATOR,
    NOT_EQUAL_COMPARATOR,
	BETWEEN_INCLUDE_COMPARATOR,
	IS_NULL,
	IS_NOT_NULL,
	DISTINCT;
	
	
	public MatchMode matchMode;
	private Expression expression;
	
	private ComparatorTypeEnum(){
	}
	
	private ComparatorTypeEnum(MatchMode matchMode, Expression expression){
    	this(matchMode);
    	this.expression = expression;
	}
	
    /**
	 * @return the expression
	 */
	public Expression getExpression() {
		return expression;
	}

	private ComparatorTypeEnum(MatchMode matchMode){
		this.matchMode = matchMode;
	}
    
	/*
	 * TODO implementar la construccionde expresiones para los ComparatorTypeEnum
	 * diferentes3
	 *  a LIKE
	 */
	
	public static abstract class Expression {

		public static final Expression LIKE_EXACT = new Expression() {

			@Override
			public String buildExpression(String value) {
				return value;
			}
			
		};
		
		public static final Expression LIKE_END = new Expression() {

			@Override
			public String buildExpression(String value) {
				return new StringBuilder("%").append(value).toString();
			}
			
		};
		
		public static final Expression LIKE_START = new Expression() {

			@Override
			public String buildExpression(String value) {
				return new StringBuilder(value).append("%").toString();
			}
			
		};
		
		public static final Expression LIKE_ANYWHERE = new Expression() {

			@Override
			public String buildExpression(String value) {
				return new StringBuilder("%").append(value).append("%").toString();
			}
			
		};
		
		public abstract String buildExpression(String value);
		
	}
}
