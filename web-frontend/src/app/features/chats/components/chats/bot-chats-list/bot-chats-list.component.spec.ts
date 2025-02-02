import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotChatsListComponent } from './bot-chats-list.component';

describe('BotChatsListComponent', () => {
  let component: BotChatsListComponent;
  let fixture: ComponentFixture<BotChatsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotChatsListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotChatsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
