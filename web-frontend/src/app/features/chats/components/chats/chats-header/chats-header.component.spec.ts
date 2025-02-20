import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatsHeaderComponent } from './chats-header.component';

describe('ChatsHeaderComponent', () => {
  let component: ChatsHeaderComponent;
  let fixture: ComponentFixture<ChatsHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChatsHeaderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChatsHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
