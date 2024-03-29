/*
 * Parallelising JVM Compiler
 *
 * Copyright 2010 Peter Calvert, University of Cambridge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package graph.state;

import graph.Type;

import graph.instructions.Producer;
import graph.instructions.Read;

import java.util.Collections;
import java.util.List;

/**
 *
 */
public class InstanceField implements State {
  private Producer object;
  private Field    field;

  public InstanceField(Producer object, Field field) {
    object.getType().unify(field.getOwner().getType(), false);

    this.object = object;
    this.field  = field;
  }

  @Override
  public Type getType() {
    return field.getType();
  }

  public Producer getObject() {
    return object;
  }

  public Field getField() {
    return field;
  }

  @Override
  public State getBase() {
    if(object instanceof Read) {
      return ((Read) object).getState().getBase();
    } else {
      return null;
    }
  }

  @Override
  public Producer[] getOperands() {
    return new Producer[] {object};
  }

  @Override
  public List<Producer> getIndicies() {
    if(object instanceof Read) {
      return ((Read) object).getState().getIndicies();
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public String toString() {
    return "->" + field;
  }
}
