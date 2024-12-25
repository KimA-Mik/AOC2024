package com.github.kima_mik.days

private const val REG_A_PREFIX = "Register A: "
private const val REG_B_PREFIX = "Register B: "
private const val REG_C_PREFIX = "Register C: "
private const val PROGRAM_PREFIX = "Program: "

class Day17 : AOCDay(17) {
    class Cocutor(
        var regA: Int = 0,
        var regB: Int = 0,
        var regC: Int = 0,
        val program: List<Int> = emptyList(),
    ) {
        private var instructionPointer: Int = 0
        private val instructions: List<Instruction> = listOf(Adv(), Bxl(), Bst(), Jnz(), Bxc(), Out(), Bdv(), Cdv())
        private val _stdOut = mutableListOf<Int>()
        val stdOut get() = _stdOut.joinToString(separator = ",")
        var halted = false
            private set

        fun run() {
            while (!halted) {
                executeCommand()
            }
        }

        fun executeCommand() {
//            println("regA = $regA, regB = $regB, regC = $regC, instructionPointer = $instructionPointer")
            if (instructionPointer !in program.indices) {
                halted = true
                return
            }

            val opcode = program[instructionPointer]
            val instruction = instructions[opcode]
            val operand = program[instructionPointer + 1]

            instruction.execute(operand)
        }

        private fun unwrapComboOperand(operand: Int): Int {
            return when (operand) {
                in 0..3 -> operand
                4 -> regA
                5 -> regB
                6 -> regC
                else -> throw Error("Invalid combo operand $operand")
            }
        }

        private sealed interface Instruction {
            fun execute(operand: Int)
        }

        private inner class Adv : Instruction {
            override fun execute(operand: Int) {
                regA = regA shr unwrapComboOperand(operand)
                instructionPointer += 2
            }
        }

        private inner class Bxl : Instruction {
            override fun execute(operand: Int) {
                regB = regB xor operand
                instructionPointer += 2
            }
        }

        private inner class Bst : Instruction {
            override fun execute(operand: Int) {
                regB = unwrapComboOperand(operand) % 8
                instructionPointer += 2
            }
        }

        private inner class Jnz : Instruction {
            override fun execute(operand: Int) {
                if (regA == 0) {
                    instructionPointer += 2
                    return
                }

                instructionPointer = operand
            }
        }

        private inner class Bxc : Instruction {
            override fun execute(operand: Int) {
                regB = regB xor regC
                instructionPointer += 2
            }
        }

        private inner class Out : Instruction {
            override fun execute(operand: Int) {
                _stdOut.add(unwrapComboOperand(operand) % 8)
                instructionPointer += 2
            }
        }

        inner class Bdv : Instruction {
            override fun execute(operand: Int) {
                regB = regA shr unwrapComboOperand(operand)
                instructionPointer += 2
            }
        }

        inner class Cdv : Instruction {
            override fun execute(operand: Int) {
                regC = regA shr unwrapComboOperand(operand)
                instructionPointer += 2
            }
        }
    }


    override fun puzzle1(input: String): String {
        val cocutor = extractInput(input)

        while (!cocutor.halted) {
            cocutor.executeCommand()
        }

        return cocutor.stdOut
    }

    private fun extractInput(input: String): Cocutor {
        val lines = input.trim().lines().filter { it.isNotBlank() }
        var regA = 0
        var regB = 0
        var regC = 0
        var program: List<Int> = emptyList()

        for (line in lines) {
            when {
                line.startsWith(REG_A_PREFIX) -> regA = line.substringAfter(REG_A_PREFIX).toInt()
                line.startsWith(REG_B_PREFIX) -> regB = line.substringAfter(REG_B_PREFIX).toInt()
                line.startsWith(REG_C_PREFIX) -> regC = line.substringAfter(REG_C_PREFIX).toInt()
                line.startsWith(PROGRAM_PREFIX) -> program =
                    line.substringAfter(PROGRAM_PREFIX).split(',').map { it.toInt() }

                else -> throw IllegalArgumentException(line)
            }
        }

        return Cocutor(regA, regB, regC, program)
    }


    override fun puzzle2(input: String): Int {
        val cocutor = extractInput(input)
        cocutor.run()
        println(cocutor.stdOut)

        return 0
    }
}