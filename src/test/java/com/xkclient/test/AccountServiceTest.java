package com.xkclient.test;

import com.xkclient.model.Account;
import com.xkclient.service.AccountService;
import com.xkclient.service.ConfigService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for AccountService
 */
public class AccountServiceTest {
    private ConfigService configService;
    private AccountService accountService;

    @Before
    public void setUp() {
        configService = new ConfigService();
        accountService = new AccountService(configService);
    }

    @Test
    public void testAddAccount() {
        Account account = new Account("TestUser", "test@example.com", "uuid-123");
        accountService.addAccount(account);
        Account retrieved = accountService.getAccount("TestUser");
        assertNotNull(retrieved);
        assertEquals("TestUser", retrieved.getUsername());
    }

    @Test
    public void testGetAccounts() {
        Account account1 = new Account("User1", "user1@example.com", "uuid-1");
        Account account2 = new Account("User2", "user2@example.com", "uuid-2");
        accountService.addAccount(account1);
        accountService.addAccount(account2);
        List<Account> accounts = accountService.getAccounts();
        assertTrue(accounts.size() >= 2);
    }

    @Test
    public void testRemoveAccount() {
        Account account = new Account("TestUser", "test@example.com", "uuid-123");
        accountService.addAccount(account);
        accountService.removeAccount("TestUser");
        Account retrieved = accountService.getAccount("TestUser");
        assertNull(retrieved);
    }

    @Test
    public void testValidateAccount() {
        Account account = new Account("TestUser", "test@example.com", "uuid-123");
        account.setAccessToken("token-123");
        assertTrue(accountService.validateAccount(account));
    }
}
