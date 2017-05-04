package com.tlcb.bdp.vo;

import java.util.ArrayList;
import java.util.List;

import com.calanger.common.dao.Expression;
import com.calanger.common.dao.ExpressionChain;
import com.tlcb.bdp.model.UserTableRelation;

public class UserTableRelationVO extends UserTableRelation {
    private static final long serialVersionUID = 1L;

    private List<ExpressionChain> expressionChainList;

    public UserTableRelationVO() {
        expressionChainList = new ArrayList<ExpressionChain>();
    }

    public UserTableRelationVO or(ExpressionChain expressionChain) {
        expressionChainList.add(expressionChain);
        return this;
    }

    public UserTableRelationVO or(Expression expression) {
        expressionChainList.add(new ExpressionChain().and(expression));
        return this;
    }

    public UserTableRelationVO and(Expression expression) {
        if (expressionChainList.isEmpty()) {
            expressionChainList.add(new ExpressionChain());
        }
        expressionChainList.get(0).and(expression);
        return this;
    }

    public List<ExpressionChain> getExpressionChainList() {
        return expressionChainList;
    }

    public void setExpressionChainList(List<ExpressionChain> expressionChainList) {
        this.expressionChainList = expressionChainList;
    }
}
