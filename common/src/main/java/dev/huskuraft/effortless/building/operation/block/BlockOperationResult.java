package dev.huskuraft.effortless.building.operation.block;

import javax.annotation.Nullable;

import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.building.operation.OperationResult;

public abstract class BlockOperationResult extends OperationResult {

    protected final BlockOperation operation;
    protected final BlockOperationResultType result;
    protected final BlockState blockStateBeforeOp;
    protected final BlockState blockStateAfterOp;

    protected BlockOperationResult(
            BlockOperation operation,
            BlockOperationResultType result,
            BlockState blockStateBeforeOp,
            BlockState blockStateAfterOp) {
        this.operation = operation;
        this.result = result;
        this.blockStateBeforeOp = blockStateBeforeOp;
        this.blockStateAfterOp = blockStateAfterOp;
    }

    @Override
    public BlockOperation getOperation() {
        return operation;
    }

    @Nullable
    public final BlockState getBlockStateToBreak() {
        return blockStateBeforeOp;
    }

    @Nullable
    public final BlockState getBlockStatePlaced() {
        return blockStateAfterOp;
    }

    @Nullable
    public final BlockState getBlockStateToPlace() {
        return getOperation().getBlockState();
    }

    @Nullable
    public final BlockState getBlockStateForRenderer() {
        if (getBlockStateToBreak() == null || getBlockStateToPlace() == null) {
            return null;
        }
        if (!getBlockStateToBreak().isAir() && getBlockStateToPlace().isAir()) {
            return getBlockStateToBreak();
        } else {
            return getBlockStateToPlace();
        }
    }

    public BlockOperationResultType result() {
        return result;
    }

}
