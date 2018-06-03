package com.company.accountservice;

import com.company.accountservice.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AccountServiceApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void shouldFetchZeroAccountsWhenThereAreNone() {
		ResponseEntity<List<Account>> accountsResponse = testRestTemplate.exchange("/accounts", HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>() {});

		assertThat(accountsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(accountsResponse.getBody()).isEmpty();
	}

}
