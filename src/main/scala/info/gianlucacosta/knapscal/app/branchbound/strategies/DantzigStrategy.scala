package info.gianlucacosta.knapscal.app.branchbound.strategies

import info.gianlucacosta.knapscal.app.branchbound.BranchBoundStrategy
import info.gianlucacosta.knapscal.knapsack.branchbound.UpperBoundFunctions

private[app] class DantzigStrategy extends BranchBoundStrategy(
  "Dantzig",
  UpperBoundFunctions.basicDantzig) {

}
