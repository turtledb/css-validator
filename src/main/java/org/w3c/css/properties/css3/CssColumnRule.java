// $Id: CssColumnRule.java,v 1.10 2012-10-18 09:46:03 ylafon Exp $
// From Sijtsche de Jong (sy.de.jong@let.rug.nl)
// Rewritten 2010 Yves Lafon <ylafon@w3.org>
//
// (c) COPYRIGHT 1995-2010  World Wide Web Consortium (MIT, ERCIM and Keio)
// Please first read the full copyright statement at
// http://www.w3.org/Consortium/Legal/copyright-software-19980720

package org.w3c.css.properties.css3;

import org.w3c.css.parser.CssStyle;
import org.w3c.css.properties.css.CssProperty;
import org.w3c.css.util.ApplContext;
import org.w3c.css.util.InvalidParamException;
import org.w3c.css.values.CssExpression;
import org.w3c.css.values.CssIdent;
import org.w3c.css.values.CssTypes;
import org.w3c.css.values.CssValue;

import static org.w3c.css.values.CssOperator.SPACE;

/**
 * @spec http://www.w3.org/TR/2011/CR-css3-multicol-20110412/#column-rule
 */

public class CssColumnRule extends org.w3c.css.properties.css.CssColumnRule {

    CssIdent value = null;
    CssColumnRuleWidth rule_width = null;
    CssColumnRuleStyle rule_style = null;
    CssColumnRuleColor rule_color = null;

    /**
     * Create a new CssColumnRule
     */
    public CssColumnRule() {
    }

    /**
     * Create a new CssColumnRule
     *
     * @param expression The expression for this property
     * @throws org.w3c.css.util.InvalidParamException Incorrect values
     */
    public CssColumnRule(ApplContext ac, CssExpression expression,
                         boolean check) throws InvalidParamException {

        CssValue val;
        char op;
        int nb_val = expression.getCount();

        if (check && nb_val > 3) {
            throw new InvalidParamException("unrecognize", ac);
        }
        setByUser();

        while (!expression.end()) {
            val = expression.getValue();
            op = expression.getOperator();
            if (op != SPACE) {
                throw new InvalidParamException("operator",
                        ((new Character(op)).toString()),
                        ac);
            }
            switch (val.getType()) {
				case CssTypes.CSS_HASH_IDENT:
                case CssTypes.CSS_FUNCTION:
                case CssTypes.CSS_COLOR:
                    if (rule_color != null) {
                        throw new InvalidParamException("unrecognize", ac);
                    }
                    rule_color = new CssColumnRuleColor(ac, expression);
                    break;
                case CssTypes.CSS_NUMBER:
                case CssTypes.CSS_LENGTH:
                    if (rule_width != null) {
                        throw new InvalidParamException("unrecognize", ac);
                    }
                    rule_width = new CssColumnRuleWidth(ac, expression);
                    break;
                case CssTypes.CSS_IDENT:
                    if (inherit.equals(val)) {
                        if (nb_val > 1) {
                            throw new InvalidParamException("unrecognize", ac);
                        }
                        value = inherit;
                        expression.next();
                        break;
                    }
                    if (rule_color == null) {
                        try {
                            rule_color = new CssColumnRuleColor(ac, expression);
                            break;
                        } catch (Exception ex) {
                        }
                    }
                    if (rule_width == null) {
                        try {
                            rule_width = new CssColumnRuleWidth(ac, expression);
                            break;
                        } catch (Exception ex) {
                        }
                    }
                    if (rule_style == null) {
                        try {
                            rule_style = new CssColumnRuleStyle(ac, expression);
                            break;
                        } catch (Exception ex) {
                        }
                    }

                default:
                    throw new InvalidParamException("value",
                            expression.getValue(),
                            getPropertyName(), ac);
            }
        }
    }

    public CssColumnRule(ApplContext ac, CssExpression expression)
            throws InvalidParamException {
        this(ac, expression, false);
    }

    /**
     * Add this property to the CssStyle
     *
     * @param style The CssStyle
     */
    public void addToStyle(ApplContext ac, CssStyle style) {
        if (((Css3Style) style).cssColumnRule != null)
            style.addRedefinitionWarning(ac, this);
        ((Css3Style) style).cssColumnRule = this;
        if (rule_style != null) {
            rule_style.addToStyle(ac, style);
        }
        if (rule_color != null) {
            rule_color.addToStyle(ac, style);
        }
        if (rule_width != null) {
            rule_width.addToStyle(ac, style);
        }
    }

    /**
     * Get this property in the style.
     *
     * @param style   The style where the property is
     * @param resolve if true, resolve the style to find this property
     */
    public CssProperty getPropertyInStyle(CssStyle style, boolean resolve) {
        if (resolve) {
            return ((Css3Style) style).getColumnRule();
        } else {
            return ((Css3Style) style).cssColumnRule;
        }
    }

    /**
     * Compares two properties for equality.
     *
     * @param property The other property.
     */
    public boolean equals(CssProperty property) {
        return false;
    }

    /**
     * Returns the value of this property
     */
    public Object get() {
        return value;
    }

    /**
     * Returns a string representation of the object
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        if (value != null) {
            return value.toString();
        }
        if (rule_color != null) {
            sb.append(rule_color);
            first = false;
        }
        if (rule_width != null) {
            if (!first) {
                sb.append(' ');
            }
            sb.append(rule_width);
        }
        if (rule_style != null) {
            if (!first) {
                sb.append(' ');
            }
            sb.append(rule_style);
        }
        return sb.toString();
    }
}
