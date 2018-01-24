/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2017 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of
 * the GNU Affero General Public License along with this program. If not, see
 * http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.fulfillment.testutils;

import org.openlmis.fulfillment.service.referencedata.FacilityOperatorDto;
import java.util.UUID;

public class FacilityOperatorDataBuilder {

  private static int instanceNumber = 0;

  private UUID id;
  private String code;
  private String name;

  /**
   * Builder for {@link FacilityOperatorDto}.
   */
  public FacilityOperatorDataBuilder() {
    instanceNumber++;

    id = UUID.randomUUID();
    code = "OP" + instanceNumber;
    name = "operator " + instanceNumber;
  }

  /**
   * Builds instance of {@link FacilityOperatorDto}.
   */
  public FacilityOperatorDto build() {
    FacilityOperatorDto operator = new FacilityOperatorDto(code, name);
    operator.setId(id);
    return operator;
  }
}
