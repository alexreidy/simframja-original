using System;
using System.Linq;
using System.Collections.Generic;

using Xunit;
using Simlibra2D;

namespace SimEntityTests
{
    class MyCompoundEntity : CompoundSimEntity<MyCompoundEntity>
    {
    }

    public class Tests
    {
        [Fact]
        public void CanGetConstituentBoxesFromCompoundEnt()
        {
            var ent = new MyCompoundEntity();

            var compoundConstituent = new MyCompoundEntity();
            compoundConstituent.AddBox(new Box(10, 10, 100, 100));
            compoundConstituent.AddBox(new Box(3, 3, 55, 55));
            compoundConstituent.AddBox(new Box(5, 5, 22, 22));            

            ent.AddConstituent(compoundConstituent);
            ent.AddBox(new Box(3, 3, 3, 3));

            Assert.True(ent.ConstituentBoxes.Count() == 4);
        }

        [Fact]
        public void CanComputeCorrectBoundingBox()
        {
            var box1 = new Box(0, 0, 100, 10);
            var box2 = new Box(300, 500, 100, 100);

            var ent = new MyCompoundEntity();
            ent.AddBox(box1);
            ent.AddBox(box2);

            var boundingBox = ent.BoundingBox;

            Assert.True(
                boundingBox.Position.X == 0 &&
                boundingBox.Position.Y == 0 &&
                boundingBox.Width == 400 &&
                boundingBox.Height == 600);
        }

        [Fact]
        public void CanDetectIfCompoundEntIsTouchingSpatial()
        {
            var ent = new MyCompoundEntity();
            ent.AddBox(new Box(0, 0, 100, 10));
            ent.AddBox(new Box(0, 50, 100, 100));

            var boxThatShouldBeTouching = new Box(5, 0, 30, 10);
            Assert.True(ent.IsTouching(boxThatShouldBeTouching));

            var boxThatShouldNotBeTouching = new Box(5, 25, 3, 3);
            Assert.False(ent.IsTouching(boxThatShouldNotBeTouching));
        }

        [Fact]
        public void CanHandleCollisionsAndGetContacts()
        {
            var a = new MyCompoundEntity();
            a.AddBox(new Box(0, 0, 100, 10));
            a.AddBox(new Box(0, 50, 100, 100));

            var b = new MyCompoundEntity();
            b.AddBox(new Box(5, 0, 30, 10));

            var ents = new List<MyCompoundEntity>();
            ents.Add(b);

            ISet<MyCompoundEntity> contacts = a.HandleCollisionsAndGetContacts(ents);

            Assert.True(contacts.Contains(b));
        }

    }
}