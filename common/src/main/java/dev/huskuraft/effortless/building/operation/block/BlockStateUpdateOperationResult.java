package dev.huskuraft.effortless.building.operation.block;

import java.util.List;

import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.building.operation.BlockSummary;
import dev.huskuraft.effortless.building.operation.Operation;
import dev.huskuraft.effortless.building.operation.empty.EmptyOperation;

public class BlockStateUpdateOperationResult extends BlockOperationResult {

    public BlockStateUpdateOperationResult(
            BlockStateUpdateOperation operation,
            BlockOperationResultType result,
            BlockState blockStateBeforeOp,
            BlockState blockStateAfterOp
    ) {
        super(operation, result, blockStateBeforeOp, blockStateAfterOp);
    }

    @Override
    public Operation getReverseOperation() {
        if (result().fail()) {
            return new EmptyOperation(operation.getContext());
        }

        return new BlockStateUpdateOperation(
                operation.getWorld(),
                operation.getPlayer(),
                operation.getContext(),
                operation.getStorage(),
                operation.getInteraction(),
                getBlockStateBeforeOp(),
                operation.getEntityState()
        );
    }

    @Override
    public List<BlockState> getBlockSummary(BlockSummary blockSummary) {
        switch (blockSummary) {
            case BLOCKS_PLACED -> {
                switch (result) {
                    case SUCCESS, SUCCESS_PARTIAL, CONSUME -> {
                        return List.of(getBlockStateInOp());
                    }
                };
            }
            case BLOCKS_DESTROYED -> {
                switch (result) {
                    case SUCCESS, SUCCESS_PARTIAL, CONSUME -> {
                        return List.of(getBlockStateBeforeOp());
                    }
                }
            }
            case BLOCKS_NOT_PLACEABLE -> {
                switch (result) {
                    case FAIL_PLACE_BLACKLISTED -> {
                        return List.of(getBlockStateInOp());
                    }
                }
            }
            case BLOCKS_NOT_BREAKABLE -> {
                switch (result) {
                    case FAIL_BREAK_REPLACE_RULE -> {
                        return List.of(getBlockStateInOp());
                    }
                }
            }
            case BLOCKS_ITEMS_INSUFFICIENT -> {
                switch (result) {
                    case FAIL_PLACE_ITEM_INSUFFICIENT -> {
                        return List.of(getBlockStateInOp());
                    }
                }
            }
            case BLOCKS_TOOLS_INSUFFICIENT -> {
                switch (result) {
                    case FAIL_BREAK_TOOL_INSUFFICIENT -> {
                        return List.of(getBlockStateBeforeOp());
                    }
                }
            }
            case BLOCKS_BLACKLISTED -> {
                switch (result) {
                    case FAIL_BREAK_BLACKLISTED -> {
                        return List.of(getBlockStateBeforeOp());
                    }
                    case FAIL_PLACE_BLACKLISTED -> {
                        return List.of(getBlockStateInOp());
                    }
                }
            }
            case BLOCKS_NO_PERMISSION -> {
                switch (result) {
                    case FAIL_BREAK_NO_PERMISSION -> {
                        return List.of(getBlockStateBeforeOp());
                    }
                    case FAIL_PLACE_NO_PERMISSION -> {
                        return List.of(getBlockStateInOp());
                    }
                }
            }
        }
        return List.of();
    }


}
