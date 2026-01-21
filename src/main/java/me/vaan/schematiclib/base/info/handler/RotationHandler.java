package me.vaan.schematiclib.base.info.handler;

import me.vaan.schematiclib.base.Rotation;

public interface RotationHandler extends BlockInfoHandler<Rotation> {

    @Override
    default Class<Rotation> type() {
        return Rotation.class;
    }

    enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST;

        /**
         * Parse a direction from a string, ignoring capitalization.
         */
        public static Direction fromString(String value) {
            if (value == null) {
                return null;
            }
            try {
                return Direction.valueOf(value.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                return null;
            }
        }

        /**
         * Return the direction after applying a rotation.
         */
        public Direction rotate(Rotation rotation) {
            switch (rotation) {
                case LEFT:
                    return rotateLeft();
                case RIGHT:
                    return rotateRight();
                case FLIP:
                    return rotateFlip();
                default:
                    throw new IllegalStateException("Unexpected rotation: " + rotation);
            }
        }

        private Direction rotateLeft() {
            switch (this) {
                case NORTH: return WEST;
                case WEST:  return SOUTH;
                case SOUTH: return EAST;
                case EAST:  return NORTH;
                default: throw new IllegalStateException();
            }
        }

        private Direction rotateRight() {
            switch (this) {
                case NORTH: return EAST;
                case EAST:  return SOUTH;
                case SOUTH: return WEST;
                case WEST:  return NORTH;
                default: throw new IllegalStateException();
            }
        }

        private Direction rotateFlip() {
            switch (this) {
                case NORTH: return SOUTH;
                case SOUTH: return NORTH;
                case EAST:  return WEST;
                case WEST:  return EAST;
                default: throw new IllegalStateException();
            }
        }

        /**
         * Return the lowercase name of the enum.
         */
        public String lowerName() {
            return name().toLowerCase();
        }
    }
}
