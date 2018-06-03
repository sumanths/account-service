package com.company.accountservice;

import com.company.accountservice.model.Account;
import com.company.accountservice.web.CreateAccountRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.Arrays;
import java.util.List;

import static com.company.accountservice.builders.AccountBuilder.account;
import static com.company.accountservice.utils.StringUtils.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AccountServiceApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Before
	public void setUp(){
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "account");
	}

	@Test
	public void shouldFetchZeroAccountsWhenThereAreNone() {
		ResponseEntity<List<Account>> accountsResponse = testRestTemplate.exchange("/accounts", HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>() {});

		assertThat(accountsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(accountsResponse.getBody()).isEmpty();
	}

	@Test
	public void shouldFetchExistingAccounts(){
		Account account1 = account();
		Account account2 = account();
		Account account3 = account();
		givenAccountExists(account1, account2, account3);

		ResponseEntity<List<Account>> accountsResponse = testRestTemplate.exchange("/accounts", HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>() {});

		assertThat(accountsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		List<Account> accountResources = accountsResponse.getBody();
		assertThat(accountResources).containsOnly(account1, account2, account3);
	}

	@Test
	public void shouldCreateNewAccount(){
		CreateAccountRequest request = CreateAccountRequest.builder()
				.firstName(randomString())
				.secondName(randomString())
				.accountNumber(randomString())
				.build();

		ResponseEntity<Account> response = testRestTemplate.postForEntity("/accounts", request, Account.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getHeaders()).containsKey(HttpHeaders.LOCATION);
		String locationOfTheCreatedResource = response.getHeaders().getValuesAsList(HttpHeaders.LOCATION).get(0);

		String createdResourceId = locationOfTheCreatedResource.substring(locationOfTheCreatedResource.lastIndexOf("/") + 1);
		Integer resourceId = Integer.parseInt(createdResourceId);

		assertThat(response.getBody()).isEqualTo(buildAccount(request, resourceId));
	}

	private Account buildAccount(CreateAccountRequest createAccountRequest, Integer resourceId) {
		return Account.builder()
				.id(resourceId)
				.firstName(createAccountRequest.getFirstName())
				.secondName(createAccountRequest.getSecondName())
				.accountNumber(createAccountRequest.getAccountNumber())
				.build();
	}

	private void givenAccountExists(Account... accounts) {
		Arrays.stream(accounts).forEach(account -> jdbcTemplate.update("insert into account(id, first_name, second_name, account_number) values (?,?,?,?)",
				account.getId(),
				account.getFirstName(),
				account.getSecondName(),
				account.getAccountNumber()));

	}

}
