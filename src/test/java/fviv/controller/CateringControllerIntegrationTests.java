/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fviv.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import fviv.AbstractWebIntegrationTests;

public class CateringControllerIntegrationTests extends AbstractWebIntegrationTests {
	
	@Autowired CateringController controller;
	
	@Test
	public void sampleMvcIntegrationTest() throws Exception {

		mvc.perform(get("/catering")). //
				andExpect(status().isOk()).//
				andExpect(model().attribute("meals", is(not(emptyIterable())))).
				andExpect(model().attribute("drinks", is(not(emptyIterable()))));
	}
	
	@Test
	public void ControllerIntegrationTest() {

		ModelMap modelMap = new ExtendedModelMap();

		String returnedView = controller.catering(modelMap);

		assertThat(returnedView, is("catering"));

	}
	
}
