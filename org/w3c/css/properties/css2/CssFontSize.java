// $Id: CssFontSize.java,v 1.1 2012-08-04 21:17:05 ylafon Exp $
// Author: Yves Lafon <ylafon@w3.org>
//
// (c) COPYRIGHT MIT, ERCIM and Keio University, 2012.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.css.properties.css2;

import org.w3c.css.util.ApplContext;
import org.w3c.css.util.InvalidParamException;
import org.w3c.css.values.CssExpression;
import org.w3c.css.values.CssIdent;
import org.w3c.css.values.CssLength;
import org.w3c.css.values.CssNumber;
import org.w3c.css.values.CssPercentage;
import org.w3c.css.values.CssTypes;
import org.w3c.css.values.CssValue;

import java.util.HashMap;

/**
 * @spec http://www.w3.org/TR/2008/REC-CSS2-20080411/fonts.html#propdef-font-size
 */
public class CssFontSize extends org.w3c.css.properties.css.CssFontSize {

	public static final HashMap<String,CssIdent> allowed_values;
	static final String[] absolute_values = {"xx-small", "x-small", "small", "medium", "large", "x-large", "xx-large"};
	static final String[] relative_values = {"smaller", "larger"};

	static {
		allowed_values = new HashMap<String, CssIdent>();
		for (String s : absolute_values) {
			allowed_values.put(s, CssIdent.getIdent(s));
		}
		for (String s : relative_values) {
			allowed_values.put(s, CssIdent.getIdent(s));
		}
	}
	/**
	 * Create a new CssFontSize
	 */
	public CssFontSize() {
	}

	/**
	 * Creates a new CssFontSize
	 *
	 * @param expression The expression for this property
	 * @throws org.w3c.css.util.InvalidParamException
	 *          Expressions are incorrect
	 */
	public CssFontSize(ApplContext ac, CssExpression expression, boolean check)
			throws InvalidParamException {
		if (check && expression.getCount() > 1) {
			throw new InvalidParamException("unrecognize", ac);
		}
		setByUser();

		CssValue val;
		char op;

		val = expression.getValue();
		op = expression.getOperator();

		switch (val.getType()) {
			case CssTypes.CSS_NUMBER:
				val = ((CssNumber)val).getLength();
			case CssTypes.CSS_LENGTH:
				CssLength l = (CssLength) val;
				if (!l.isPositive()) {
					throw new InvalidParamException("negative-value",
							val.toString(), ac);
				}
				value = l;
				break;
			case CssTypes.CSS_PERCENTAGE:
				CssPercentage p = (CssPercentage) val;
				if (!p.isPositive()) {
					throw new InvalidParamException("negative-value",
							val.toString(), ac);
				}
				value = p;
				break;
			case CssTypes.CSS_IDENT:
				CssIdent ident = (CssIdent) val;
				if (inherit.equals(ident)) {
					value = inherit;
					break;
				}
				value = allowed_values.get(val.toString());
				if (value == null) {
					throw new InvalidParamException("value",
							expression.getValue().toString(),
							getPropertyName(), ac);
				}
				 break;
			default:
				throw new InvalidParamException("value",
						expression.getValue().toString(),
						getPropertyName(), ac);
		}
		expression.next();
	}

	public CssFontSize(ApplContext ac, CssExpression expression)
			throws InvalidParamException {
		this(ac, expression, false);
	}


}
