/*
 * Copyright (2023) The Delta Lake Project Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.delta.kernel.expressions;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.delta.kernel.annotation.Evolving;
import io.delta.kernel.client.ExpressionHandler;

/**
 * Defines predicate scalar expression which is an extension of {@link ScalarExpression}
 * that evaluates to true, false, or null for each input row.
 * <p>
 * Currently, implementations of {@link ExpressionHandler} requires support for at least the
 * following scalar expressions.
 * <ol>
 *  <li>Name: <code>=</code>
 *   <ul>
 *    <li>SQL semantic: <code>expr1 = expr2</code></li>
 *    <li>Since version: 3.0.0</li>
 *   </ul>
 *  </li>
 *  <li>Name: <code>&lt;</code>
 *   <ul>
 *    <li>SQL semantic: <code>expr1 &lt; expr2</code></li>
 *    <li>Since version: 3.0.0</li>
 *   </ul>
 *  </li>
 *  <li>Name: <code>&lt;=</code>
 *   <ul>
 *    <li>SQL semantic: <code>expr1 &lt;= expr2</code></li>
 *    <li>Since version: 3.0.0</li>
 *   </ul>
 *  </li>
 *  <li>Name: <code>&gt;</code>
 *   <ul>
 *    <li>SQL semantic: <code>expr1 &gt; expr2</code></li>
 *    <li>Since version: 3.0.0</li>
 *   </ul>
 *  </li>
 *  <li>Name: <code>&gt;=</code>
 *   <ul>
 *    <li>SQL semantic: <code>expr1 &gt;= expr2</code></li>
 *    <li>Since version: 3.0.0</li>
 *   </ul>
 *  </li>
 *  <li>Name: <code>ALWAYS_TRUE</code>
 *   <ul>
 *    <li>SQL semantic: <code>Constant expression whose value is `true`</code></li>
 *    <li>Since version: 3.0.0</li>
 *   </ul>
 *  </li>
 *  <li>Name: <code>ALWAYS_FALSE</code>
 *   <ul>
 *    <li>SQL semantic: <code>Constant expression whose value is `false`</code></li>
 *    <li>Since version: 3.0.0</li>
 *   </ul>
 *  </li>
 *  <li>Name: <code>AND</code>
 *   <ul>
 *    <li>SQL semantic: <code>expr1 AND expr2</code></li>
 *    <li>Since version: 3.0.0</li>
 *   </ul>
 *  </li>
 *  <li>Name: <code>OR</code>
 *   <ul>
 *    <li>SQL semantic: <code>expr1 OR expr2</code></li>
 *    <li>Since version: 3.0.0</li>
 *   </ul>
 *  </li>
 * </ol>
 *
 * @since 3.0.0
 */
@Evolving
public class Predicate extends ScalarExpression {
    public Predicate(String name, List<Expression> children) {
        super(name, children);
    }

    @Override
    public String toString() {
        if (BINARY_OPERATORS.contains(name)) {
            return String.format("(%s %s %s)", children.get(0), name, children.get(1));
        }
        return super.toString();
    }

    private static final Set<String> BINARY_OPERATORS =
        Stream.of("<", "<=", ">", ">=", "=", "AND", "OR").collect(Collectors.toSet());
}
