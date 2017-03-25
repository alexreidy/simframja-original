using System;
using System.Collections.Generic;
using System.Threading;
using System.Linq;
using WebCanvasCore;
using Simlibra2D;

namespace ConsoleApplication
{
    public class Program
    {
        private static Random rng = new Random();
        class ColoredBox : Box
        {
            public string Color { get; set; } = "black";

            public ColoredBox(float x, float y, float width, float height, string color = "black")
                : base(x, y, width, height)
            {
                this.Color = color;
                _originalColor = color;
            }

            private string _originalColor = "black";
        }

        class GameEntity : CompoundSimEntity<GameEntity>
        {
            private int _hp = 100;
            public int Hitpoints
            {
                get { return _hp; }
                set
                {
                    if (value < _hp)
                    {
                        Console.WriteLine("Ouch!");
                    }
                    _hp = value;
                }
            }

            public GameEntity()
            {
                var physical = new StandardPhysical();
                physical.TargetSpatial = this;
                this.BackingPhysical = physical;
            }

            public override void WhileTouching(GameEntity entity)
            {
                entity.ApplyForce(this.Velocity.X*0.2f, this.Velocity.Y*0.2f);
                this.ApplyForce(-this.Velocity.X*0.2f, -this.Velocity.Y*0.2f);
            }

            public override void OnCollisionWith(GameEntity entity)
            {

            }

            public int Energy { get; set; }

            public bool Up { get; set; }

            public virtual void Update()
            {
                if (rng.NextDouble() > 0.95)
                {
                    this.ApplyForce((float)rng.NextDouble()*1, (float)rng.NextDouble()*1);
                }
                this.UpdatePhysics();
            }
        }

        class Background : GameEntity
        {
            public Background()
            {
                this.AddBox(new ColoredBox(0, 0, 2000, 2000, "black"));
                this.IsPhantom = true;
            }

            public override void WhileTouching(GameEntity entity)
            {
                entity.ApplyForce(-1f * entity.Velocity.X, -1f * entity.Velocity.Y);
            }

            public override void Update()
            {

            }
        }

        class Phantom : GameEntity
        {
            public Phantom(float x, float y, float width, float height, string color = "black")
            {
                var box = new ColoredBox(x, y, width, height, color);
                this.AddBox(box);
                this.IsPhantom = true;
            }
            public override void OnCollisionWith(GameEntity entity)
            {
                /*
                if (entity.Position.X < this.Position.X)
                {
                    entity.ApplyForce(-110f, 0);
                }
                else
                {
                    entity.ApplyForce(110f, 0);
                }*/
                entity.ApplyForce(-(entity.Velocity.X*3f + 17f), -(entity.Velocity.Y*3f + 17f));
            }

            public override void Update()
            {
            }
        }

        class Piston : Phantom
        {
            public Piston(float x, float y, float width, float height, string color = "black")
                : base(x, y, width, height, color)
            {

            }

            public Phantom Parent { get; set; }

            public override void OnCollisionWith(GameEntity entity)
            {
                entity.Hitpoints--;
            }

            bool left = true;
            public override void Update()
            {
                if (left)
                {
                    this.Move(-5f, 0);
                }
                else
                {
                    this.Move(5f, 0);
                }
                if (this.Position.X < this.Parent.Position.X - 13)
                {
                    this.left = false;
                }
                if (this.Position.X > this.Parent.Position.X + 13)
                {
                    this.left = true;
                }

                
            }

        }

        static void DrawColoredBox(ColoredBox box, WebCanvas canvas)
        {
            canvas.SetFillStyle(box.Color);
            canvas.DrawRect(box.Position.X, box.Position.Y, box.ConstituentBoxes.First().Width, box.ConstituentBoxes.First().Height);
        }

        public static void Main(string[] args)
        {
            var canvas = new WebCanvas();
            bool running = true;

            Console.CancelKeyPress += (object sender, ConsoleCancelEventArgs a) =>
            {
                canvas.Shutdown();
                running = false;
            };

            const int Width = 1200, Height = 800;

            bool ready = false;

            canvas.RunServingDefaultHtmlPage(8442, onReadyToRender: () => {
                canvas.SetSize(Width, Height);
                ready = true;        
            });

            var background = new Background();

            var ents = new List<GameEntity>();

            ents.Add(background);

            for (int i = 0; i < 100; i++)
            {
                var ent = new GameEntity();
                ent.AddBox(new ColoredBox(0, 0, 3, 3));
                ent.AddBox(new ColoredBox(0, 10, 15, 15, "orange"));
                ent.AddBox(new ColoredBox(15, 0, 8, 25, "purple"));
                ent.SetPosition((float)rng.NextDouble() * (float)Width, (float)rng.NextDouble() * (float)Height);
                ents.Add(ent);
                float s1 = rng.NextDouble() > 0.5 ? -1 : 1;
                float s2 = rng.NextDouble() > 0.5 ? -1 : 1;
                ent.SetVelocity(s1*(float)rng.NextDouble(), s2*(float)rng.NextDouble());
            }

            var phantom = new Phantom(0,0,50,400,"purple");
            var piston = new Piston(0, 10, 30, 390, "yellow");
            piston.Parent = phantom;
            phantom.AddConstituent(piston);
            ents.Add(phantom);

            while (running)
            {
                if (!ready) goto sleep;
                WebCanvasCore.IVector2f mpos = canvas.MousePosition;

                phantom.SetPosition(mpos.X, mpos.Y);

                canvas.StartUpdateBatch();

                foreach (var box in phantom.ConstituentBoxes)
                {
                    DrawColoredBox((ColoredBox)box, canvas);
                }
                
                foreach (GameEntity ent in ents)
                {
                    if (!ent.IsTouching(background))
                    {
                        ent.SetPosition((float)rng.NextDouble() * Width, (float)rng.NextDouble()*Height);
                    }

                    ent.Update();
                    /*
                    ent.HandleCollisionsAndGetContacts(
                        ents.Where(e => e != ent && e.BoundingBox.IsTouching(ent.BoundingBox)));*/

                    
                    var potentialContacts = new List<GameEntity>();
                    foreach (GameEntity e in ents)
                    {
                        if (e == ent) continue;

                        if (e.BoundingBox.IsTouching(ent.BoundingBox))
                        {
                            potentialContacts.Add(e);
                        }
                    }

                    ent.HandleCollisionsAndGetContacts(potentialContacts);


                    foreach (Box box in ent.ConstituentBoxes)
                    {
                        DrawColoredBox((ColoredBox)box, canvas);
                    }

                    Box boundingBox = ent.BoundingBox;
                    canvas.SetStrokeStyle("red");
                    canvas.DrawLine(
                        boundingBox.Position.X,
                        boundingBox.Position.Y,
                        boundingBox.Position.X + boundingBox.Width,
                        boundingBox.Position.Y, 1);

                    canvas.DrawLine(
                        boundingBox.Position.X,
                        boundingBox.Position.Y,
                        boundingBox.Position.X,
                        boundingBox.Position.Y + boundingBox.Height, 1);

                    canvas.DrawLine(
                        boundingBox.Position.X,
                        boundingBox.Position.Y + boundingBox.Height,
                        boundingBox.Position.X + boundingBox.Width,
                        boundingBox.Position.Y + boundingBox.Height, 1);

                }

                canvas.SetFillStyle("green");

                canvas.ApplyUpdateBatch();
            
sleep:
                Thread.Sleep(50);
            }
        }
    }
}