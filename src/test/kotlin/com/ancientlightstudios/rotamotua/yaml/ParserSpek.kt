package com.ancientlightstudios.rotamotua.yaml

import org.amshove.kluent.`should equal`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

class ParserSpek : Spek({

    given("a parser") {
        on("parsing a file") {

            val result = YamlParser.parsePlan(ParserSpek::class.java.getResource("/ParserSpek.yml"))
            it("has agents in it") {
                result.agents.size `should equal` 3
            }

            val firstAgent = result.agents[0]
            it ("has properly read the first agent") {
                firstAgent.name `should equal` ""
                firstAgent.connections.size `should equal` 1
                firstAgent.connections[0].output `should equal` "output"
                firstAgent.connections[0].target `should equal` "SomeAgent"
                firstAgent.connections[0].input `should equal` "input"
            }

            val secondAgent = result.agents[1]
            it ("has properly read the second agent") {
                secondAgent.name `should equal` "SomeAgent"
                secondAgent.settings["text"]  `should equal` "Hello World!"
            }

            val thirdAgent = result.agents[2]
            it ("has properly read the third agent") {
                thirdAgent.name `should equal` ""
                thirdAgent.type `should equal` ""
                thirdAgent.settings["whatever"]  `should equal` 5
                thirdAgent.connections.size `should equal` 2
                thirdAgent.connections[0].output `should equal` "out"
                thirdAgent.connections[0].target `should equal` "SomeAgent"
                thirdAgent.connections[0].input `should equal` "in"
                thirdAgent.connections[1].output `should equal` "output"
                thirdAgent.connections[1].target `should equal` "AnotherAgent"
                thirdAgent.connections[1].input `should equal` "input"
            }

        }
    }

})
