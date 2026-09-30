package info.gianlucacosta.knapscal.app.branchbound

import info.gianlucacosta.knapscal.knapsack.Problem
import info.gianlucacosta.knapscal.knapsack.branchbound.{BranchBoundSolver, UpperBoundFunction}

abstract class BranchBoundStrategy(name: String, upperBoundFunction: UpperBoundFunction) {
  def run(problem: Problem): Unit = {
    val solver = new BranchBoundSolver(upperBoundFunction)
    val solution = solver.solve(problem)

    val solutionDialog = new SolutionDialog(problem, solution)
    solutionDialog.showAndWait()
  }

  override def toString: String = name
}
