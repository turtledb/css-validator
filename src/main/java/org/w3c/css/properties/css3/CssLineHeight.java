// $Id: CssLineHeight.java,v 1.4 2012-09-10 17:04:58 ylafon Exp $
// Author: Yves Lafon <ylafon@w3.org>
//
// (c) COPYRIGHT MIT, ERCIM and Keio University, 2012.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.css.properties.css3;

import org.w3c.css.util.ApplContext;
import org.w3c.css.util.InvalidParamException;
import org.w3c.css.values.CssExpression;
import org.w3c.css.values.CssIdent;
import org.w3c.css.values.CssLength;
import org.w3c.css.values.CssNumber;
import org.w3c.css.values.CssPercentage;
import org.w3c.css.values.CssTypes;
import org.w3c.css.values.CssValue;

/**
 * @version $Revision: 1.4 $
 * @spec http://www.w3.org/TR/2002/WD-css3-linebox-20020515/#line-height
 */
public class CssLineHeight extends org.w3c.css.properties.css.CssLineHeight {

	public static final CssIdent normal = CssIdent.getIdent("normal");

	/**
	 * Create a new CssLineHeight
	 */
	public CssLineHeight() {
		value = initial;
	}

	/**
	 * Creates a new CssLineHeight
	 *
	 * @param expression The expression for this property
	 * @throws org.w3c.css.util.InvalidParamException
	 *          Expressions are incorrect
	 */
	public CssLineHeight(ApplContext ac, CssExpression expression, boolean check)
			throws InvalidParamException {
		if (check && expression.getCount() > 1) {
			throw new InvalidParamException("unrecognize", ac);
		}
		setByUser();
		CssValue val = expression.getValue();

		switch (val.getType()) {
			case CssTypes.CSS_IDENT:
				if (inherit.equals(val)) {
					value = inherit;
				} else if (normal.equals(val)) {
					value = normal;
				} else if (none.equals(val)) {
					value = none;
				} else {
					throw new InvalidParamException("value", val.toString(),
							getPropertyName(), ac);
				}
				break;
			case CssTypes.CSS_LENGTH:
				CssLength length = val.getLength();
				length.checkPositiveness(ac, this);
				value = val;
				break;
			case CssTypes.CSS_NUMBER:
				CssNumber number = val.getNumber();
				number.checkPositiveness(ac, this);
				value = val;
				break;
			case CssTypes.CSS_PERCENTAGE:
				CssPercentage percent = val.getPercentage();
				percent.checkPositiveness(ac, this);
				value = val;
				break;
			default:
				throw new InvalidParamException("value", val.toString(),
						getPropertyName(), ac);
		}
		expression.next();
	}

	public CssLineHeight(ApplContext ac, CssExpression expression)
			throws InvalidParamException {
		this(ac, expression, false);
	}


}

