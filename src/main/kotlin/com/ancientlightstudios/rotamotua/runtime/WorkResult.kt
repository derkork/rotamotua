package com.ancientlightstudios.rotamotua.runtime

enum class WorkResult {
    /**
     * Agent is done working and will never work again.
     */
    Finished,
    /**
     * Agent performed no work in this cycle but may get work later.
     */
    Idle,
    /**
     * Agent performed work in this cycle.
     */
    Working
}