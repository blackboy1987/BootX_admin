
package com.igomall.dao.member.impl;

import java.math.BigDecimal;

import javax.persistence.NoResultException;

import com.igomall.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.igomall.dao.member.MemberRankDao;
import com.igomall.entity.member.MemberRank;

/**
 * Dao - 会员等级
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Repository
public class MemberRankDaoImpl extends BaseDaoImpl<MemberRank, Long> implements MemberRankDao {

	public MemberRank findDefault() {
		try {
			String jpql = "select memberRank from MemberRank memberRank where memberRank.isDefault = true";
			return entityManager.createQuery(jpql, MemberRank.class).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MemberRank findByAmount(BigDecimal amount) {
		String jpql = "select memberRank from MemberRank memberRank where memberRank.isSpecial = false and memberRank.amount <= :amount order by memberRank.amount desc";
		return entityManager.createQuery(jpql, MemberRank.class).setParameter("amount", amount).setMaxResults(1).getSingleResult();
	}

	public void clearDefault() {
		String jpql = "update MemberRank memberRank set memberRank.isDefault = false where memberRank.isDefault = true";
		entityManager.createQuery(jpql).executeUpdate();
	}

	public void clearDefault(MemberRank exclude) {
		Assert.notNull(exclude,"");

		String jpql = "update MemberRank memberRank set memberRank.isDefault = false where memberRank.isDefault = true and memberRank != :exclude";
		entityManager.createQuery(jpql).setParameter("exclude", exclude).executeUpdate();
	}

}