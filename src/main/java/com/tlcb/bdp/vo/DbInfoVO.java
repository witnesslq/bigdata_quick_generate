package com.tlcb.bdp.vo;

import java.util.ArrayList;
import java.util.List;

import com.calanger.common.dao.Expression;
import com.calanger.common.dao.ExpressionChain;
import com.tlcb.bdp.model.DbInfo;

public class DbInfoVO extends DbInfo {
    private static final long serialVersionUID = 1L;

    private List<ExpressionChain> expressionChainList;

    public DbInfoVO() {
        expressionChainList = new ArrayList<ExpressionChain>();
    }

    public DbInfoVO or(ExpressionChain expressionChain) {
        expressionChainList.add(expressionChain);
        return this;
    }

    public DbInfoVO or(Expression expression) {
        expressionChainList.add(new ExpressionChain().and(expression));
        return this;
    }

    public DbInfoVO and(Expression expression) {
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
