package de.esempe.workflow.boundary.db.listener;

import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

import de.esempe.workflow.domain.User;

@Component
public class CommonRepositoryEventListener extends AbstractRepositoryEventListener<User>
{

	@Override
	protected void onAfterCreate(User user)
	{
		System.out.println("User created: " + user);
	}

	@Override
	public void onBeforeSave(User entity)
	{
		System.out.println("CommontRepositoryEventListener::onBeforeSave");
	}

	@Override
	public void onAfterSave(User entity)
	{
		System.out.println("CommontRepositoryEventListener::onAfterSave");
	}

	@Override
	public void onBeforeDelete(User entity)
	{
		// TODO Auto-generated method stub
		System.out.println("CommontRepositoryEventListener::onBeforeDelete");
	}
}
